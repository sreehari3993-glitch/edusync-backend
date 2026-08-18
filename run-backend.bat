@echo off
setlocal
echo ===================================================
echo Starting EduSync Spring Boot Backend
echo ===================================================

REM Set JAVA_HOME if not already set
if "%JAVA_HOME%"=="" (
    if exist "C:\Users\sreeh\.jdks\openjdk-26.0.1" (
        set "JAVA_HOME=C:\Users\sreeh\.jdks\openjdk-26.0.1"
    ) else if exist "C:\Program Files\Eclipse Adoptium\jdk-17.0.0.0-hotspot" (
        set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.0.0-hotspot"
    )
)

if not "%JAVA_HOME%"=="" (
    set "PATH=%JAVA_HOME%\bin;%PATH%"
    echo Using JAVA_HOME: %JAVA_HOME%
)

REM Find Maven
set "MVN_CMD=mvn"
where mvn >nul 2>nul
if %ERRORLEVEL% neq 0 (
    if exist "C:\Program Files\JetBrains\IntelliJ IDEA 2026.1.2\plugins\maven\lib\maven3\bin\mvn.cmd" (
        set "MVN_CMD=C:\Program Files\JetBrains\IntelliJ IDEA 2026.1.2\plugins\maven\lib\maven3\bin\mvn.cmd"
    )
)

echo Running Maven command: "%MVN_CMD%" spring-boot:run
echo Web App will be available at: http://localhost:8081
echo ===================================================

"%MVN_CMD%" spring-boot:run
