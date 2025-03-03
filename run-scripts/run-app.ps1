
# Ensure bin directory exists
if (-not (Test-Path -Path "bin")) {
    New-Item -Path "bin" -ItemType Directory -Force | Out-Null
}

# Function to build classpath with all JAR files
function Build-RuntimeClasspath {
    $cp = "bin"
    
    # Add all JAR files from lib directory
    if (Test-Path -Path "lib") {
        $jarFiles = Get-ChildItem -Path "lib" -Filter "*.jar" -ErrorAction SilentlyContinue
        foreach ($jar in $jarFiles) {
            $cp = "$cp;$($jar.FullName)"
        }
    }
    
    return $cp
}

# Clean the bin directory
Write-Host "Cleaning bin directory..." -ForegroundColor Yellow
& "$PSScriptRoot\clean_dirs\clean_dir.ps1"

# Compile the source code
Write-Host "Compiling source code..." -ForegroundColor Yellow
& "$PSScriptRoot\compile\compile.ps1"

# Check if compilation was successful
if ($LASTEXITCODE -ne 0) {
    Write-Host "Compilation failed" -ForegroundColor Red
    exit 1
}

# Get the classpath with all JARs
$ClassPath = Build-RuntimeClasspath

# Run the tests script
Write-Host "Running tests..." -ForegroundColor Yellow
& "$PSScriptRoot\run\run.ps1" -AppName "Test"

# Run the main app
Write-Host "Running application..." -ForegroundColor Yellow
& "$PSScriptRoot\run\run.ps1" -AppName "App"
