#!/bin/bash
# Ensure bin directory exists
mkdir -p bin

# Build classpath with all JAR files
build_runtime_classpath() {
    local cp="bin"
    
    # Add all JAR files from lib directory
    if [ -d "lib" ]; then
        local jar_files=$(find "lib" -name "*.jar" 2>/dev/null)
        for jar in $jar_files; do
            cp="$cp:$jar"
        done
    fi
    
    echo "$cp"
}

# Clean the bin directory
echo -e "\033[1;33mCleaning bin directory...\033[0m"
run-scripts/clean_dirs/clean_dir.sh

# Compile the source code
echo -e "\033[1;33mCompiling source code...\033[0m"
run-scripts/compile/compile.sh

# Check if compilation was successful
if [ $? -ne 0 ]; then
    echo -e "\033[1;31mCompilation failed\033[0m"
    exit 1
fi

# Get the classpath with all JARs
CLASSPATH=$(build_runtime_classpath)

# Run the tests script
echo -e "\033[1;33mRunning tests...\033[0m"
source run-scripts/run/run.sh Test

# Run the main app
echo -e "\033[1;33mRunning application...\033[0m"
source run-scripts/run/run.sh App