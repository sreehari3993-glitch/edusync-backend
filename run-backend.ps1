# EduSync Spring Boot Runner
Write-Host "===================================================" -ForegroundColor Cyan
Write-Host "Starting EduSync Spring Boot Backend" -ForegroundColor Cyan
Write-Host "===================================================" -ForegroundColor Cyan

if (-not $env:JAVA_HOME) {
    if (Test-Path "C:\Users\sreeh\.jdks\openjdk-26.0.1") {
        $env:JAVA_HOME = "C:\Users\sreeh\.jdks\openjdk-26.0.1"
    }
}

if ($env:JAVA_HOME) {
    $env:Path = "$env:JAVA_HOME\bin;$env:Path"
    Write-Host "Using JAVA_HOME: $env:JAVA_HOME" -ForegroundColor Green
}

$mvnCmd = "mvn"
if (-not (Get-Command "mvn" -ErrorAction SilentlyContinue)) {
    if (Test-Path "C:\Program Files\JetBrains\IntelliJ IDEA 2026.1.2\plugins\maven\lib\maven3\bin\mvn.cmd") {
        $mvnCmd = "C:\Program Files\JetBrains\IntelliJ IDEA 2026.1.2\plugins\maven\lib\maven3\bin\mvn.cmd"
    }
}

Write-Host "Running Maven: $mvnCmd spring-boot:run" -ForegroundColor Yellow
Write-Host "Web app URL: http://localhost:8081" -ForegroundColor Green
Write-Host "===================================================" -ForegroundColor Cyan

& $mvnCmd spring-boot:run
