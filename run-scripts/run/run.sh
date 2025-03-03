#!/bin/bash

# Get the app name from command line parameter or default to "App"
APP_NAME=${1:-"App"}

# Determine the classpath separator based on OS
if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "win32" ]]; then
    SEPARATOR=";"
else
    SEPARATOR=":"
fi

# Build the classpath string
CLASSPATH="bin"

# Add all JAR files in the lib directory
if [ -d "lib" ]; then
    for jar in lib/*.jar; do
        if [ -f "$jar" ]; then
            CLASSPATH="${CLASSPATH}${SEPARATOR}${jar}"
        fi
    done
fi

# Run the application
echo "Running ${APP_NAME} with classpath: $CLASSPATH"
java -cp "$CLASSPATH" "$APP_NAME"