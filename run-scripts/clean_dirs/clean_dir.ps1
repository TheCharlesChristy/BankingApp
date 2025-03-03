
Write-Host "Cleaning bin directory..." -ForegroundColor Yellow

if (Test-Path -Path "bin") {
    $items = Get-ChildItem -Path "bin" -Force
    
    foreach ($item in $items) {
        Write-Host "Removing $($item.FullName)" -ForegroundColor Red
        Remove-Item -Path $item.FullName -Force -Recurse
    }
}
else {
    Write-Host "bin directory does not exist"
}
