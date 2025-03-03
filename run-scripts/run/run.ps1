
# Get the app name from command line parameter or default to "App"
param (
    [string]$AppName = "App"
)

# Build the classpath string
$ClassPath = "bin"

# Add all JAR files in the lib directory
if (Test-Path "lib") {
    $JarFiles = Get-ChildItem -Path "lib" -Filter "*.jar"
    foreach ($jar in $JarFiles) {
        $ClassPath = "$ClassPath;$($jar.FullName)"
    }
}

# Run the application
Write-Host "Running $AppName with classpath: $ClassPath"
java -cp $ClassPath $AppName
