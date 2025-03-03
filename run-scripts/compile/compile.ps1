
param (
    [string]$ProjectRoot = (Get-Location)
)

# Function to display error messages
function Show-Error {
    param([string]$Message)
    Write-Host "ERROR: $Message" -ForegroundColor Red
    exit 1
}

# Function to display info messages
function Show-Info {
    param([string]$Message)
    Write-Host "INFO: $Message" -ForegroundColor Blue
}

# Check if Java is installed
try {
    $null = Get-Command javac -ErrorAction Stop
}
catch {
    Show-Error "Java compiler (javac) not found. Please install JDK."
}

# Navigate to project root
try {
    Push-Location -Path $ProjectRoot
}
catch {
    Show-Error "Could not change to directory $ProjectRoot"
}

# Define the output directory
$BinDir = Join-Path -Path $ProjectRoot -ChildPath "bin"

# Create bin directory if it doesn't exist
if (-not (Test-Path $BinDir)) {
    try {
        New-Item -Path $BinDir -ItemType Directory -Force | Out-Null
        Show-Info "Output directory: $BinDir"
    }
    catch {
        Show-Error "Failed to create bin directory"
    }
}

# Find all .java files
$JavaFiles = Get-ChildItem -Path $ProjectRoot -Filter "*.java" -Recurse | 
    Where-Object { $_.FullName -notlike "*\bin\*" } | 
    Select-Object -ExpandProperty FullName

# Check if any Java files were found
if (-not $JavaFiles) {
    Show-Error "No Java source files found in $ProjectRoot"
}
Show-Info "Found $($JavaFiles.Count) Java source files"

# Check for lib directory with JAR files
$ClassPath = $BinDir
if (Test-Path (Join-Path -Path $ProjectRoot -ChildPath "lib")) {
    $JarFiles = Get-ChildItem -Path (Join-Path -Path $ProjectRoot -ChildPath "lib") -Filter "*.jar"
    foreach ($jar in $JarFiles) {
        $ClassPath = "$ClassPath;$($jar.FullName)"
    }
    Show-Info "Added lib directory JARs to classpath"
}

# Clean bin directory before compilation
Show-Info "Cleaning bin directory"
Get-ChildItem -Path $BinDir -Recurse | Remove-Item -Force -Recurse

# Compile all Java files
Show-Info "Compiling Java files..."
$JavaFilesString = $JavaFiles -join " "
$CompileCommand = "javac -d `"$BinDir`" -cp `"$ClassPath`" $JavaFilesString"

try {
    Invoke-Expression $CompileCommand
    if ($LASTEXITCODE -eq 0) {
        Show-Info "Compilation successful! Class files are in $BinDir"
    }
    else {
        Show-Error "Compilation failed"
    }
}
catch {
    Show-Error "Compilation failed: $_"
}

# Copy any resource files (optional)
Show-Info "Copying resource files..."
Get-ChildItem -Path $ProjectRoot -Directory -Recurse | 
    Where-Object { $_.FullName -notlike "*\.git*" -and $_.FullName -notlike "*\bin*" } | 
    ForEach-Object {
        $SourceDir = $_.FullName
        $RelativePath = $SourceDir.Substring($ProjectRoot.Length).TrimStart('\')
        $TargetDir = Join-Path -Path $BinDir -ChildPath $RelativePath
        
        if (-not (Test-Path $TargetDir)) {
            New-Item -Path $TargetDir -ItemType Directory -Force | Out-Null
        }
        
        Get-ChildItem -Path $SourceDir -File | 
            Where-Object { $_.Extension -ne ".java" -and $_.Extension -ne ".class" } | 
            ForEach-Object {
                Copy-Item -Path $_.FullName -Destination $TargetDir -Force
            }
    }

Show-Info "Build completed successfully"

# Return to original directory
Pop-Location
