@echo off
set DIR=%~dp0
if "%JAVA_HOME%"=="" (set JAVA=java) else (set JAVA=%JAVA_HOME%\bin\java.exe)
"%JAVA%" %JAVA_OPTS% %GRADLE_OPTS% -Dorg.gradle.appname=gradlew -classpath "%DIR%gradle\wrapper\gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %*
