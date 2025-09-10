@ECHO OFF
SETLOCAL
SET WRAPPER_JAR=.mvn\wrapper\maven-wrapper.jar
IF DEFINED JAVA_HOME (
  "%JAVA_HOME%\bin\java.exe" -Dmaven.multiModuleProjectDirectory="%CD%" -cp "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
) ELSE (
  java -Dmaven.multiModuleProjectDirectory="%CD%" -cp "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
)
