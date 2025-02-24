# Create build directory if not exists
Write-Host "Creating build directory..." -ForegroundColor Yellow
New-Item -ItemType Directory -Force -Path ".\bin"

# Make sure the bin directory is empty
Write-Host "Cleaning bin directory..." -ForegroundColor Yellow
Get-ChildItem -Path ".\bin\*" | ForEach-Object {
    Write-Host "Removing $($_.FullName)" -ForegroundColor Red
    Remove-Item -Recurse -Force -Path $_.FullName
}

# Dynamically build the classpath from JAR files in the lib folder
$jarFiles = Get-ChildItem -Recurse -Filter "*.jar" -Path ".\lib"

# Build the dynamic classpath from JAR files
$jarClasspath = $jarFiles.FullName -join ";"
$classpath = ".\bin;$jarClasspath"

# First compile all Java files within src (and lib if needed)
Write-Host "Compiling source files from src..." -ForegroundColor Green
Get-ChildItem -Recurse -Filter "*.java" -Path .\src | ForEach-Object {
    Write-Host "Compiling $($_.Name)..." -ForegroundColor Cyan
    javac -cp $classpath -d .\bin $_.FullName
}

Write-Host "Compiling source files from lib..." -ForegroundColor Green
Get-ChildItem -Recurse -Filter "*.java" -Path .\lib | ForEach-Object {
    Write-Host "Compiling $($_.Name)..." -ForegroundColor Cyan
    javac -cp $classpath -d .\bin $_.FullName
}

# Now compile the main App.java from the project root
Write-Host "Compiling App.java..." -ForegroundColor Green
javac -cp $classpath -d .\bin App.java

Write-Host "JAR Files:" -ForegroundColor Magenta
$jarFiles | ForEach-Object {
    Write-Host $_.FullName -ForegroundColor DarkMagenta
}

# Run the main app (make sure App.java has no package declaration)
Write-Host "Running application..." -ForegroundColor Yellow
java -cp ".\bin;$jarClasspath" App