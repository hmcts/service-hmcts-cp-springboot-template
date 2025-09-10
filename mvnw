#!/bin/sh
set -e
WRAPPER_JAR=".mvn/wrapper/maven-wrapper.jar"
JAVA_EXE="${JAVA_HOME:+$JAVA_HOME/bin/}java"
[ -x "$JAVA_EXE" ] || JAVA_EXE="java"
exec "$JAVA_EXE" \
  -Dmaven.multiModuleProjectDirectory="$PWD" \
  -cp "$WRAPPER_JAR" org.apache.maven.wrapper.MavenWrapperMain "$@"
