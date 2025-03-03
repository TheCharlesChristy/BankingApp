#!/bin/bash

# Java Project Compilation Script

# Function to display error messages
error() {
    echo -e "\033[0;31mERROR: $1\033[0m" >&2
    exit 1
}

# Function to display info messages
info() {
    echo -e "\033[0;34mINFO: $1\033[0m"
}

# Check if Java is installed
if ! command -v javac &> /dev/null; then
    error "Java compiler (javac) not found. Please install JDK."
fi

# Get the project root directory (default: current directory)
PROJECT_ROOT="${1:-.}"
cd "$PROJECT_ROOT" || error "Could not change to directory $PROJECT_ROOT"

# Define the output directory
BIN_DIR="$PROJECT_ROOT/bin"

# Create bin directory if it doesn't exist
mkdir -p "$BIN_DIR" || error "Failed to create bin directory"
info "Output directory: $BIN_DIR"

# Find all .java files
JAVA_FILES=$(find "$PROJECT_ROOT" -name "*.java" -type f | grep -v "$BIN_DIR")

# Check if any Java files were found
if [ -z "$JAVA_FILES" ]; then
    error "No Java source files found in $PROJECT_ROOT"
fi
info "Found $(echo "$JAVA_FILES" | wc -l) Java source files"

# Check for lib directory with JAR files
CLASSPATH="$BIN_DIR"
if [ -d "$PROJECT_ROOT/lib" ]; then
    for jar in "$PROJECT_ROOT"/lib/*.jar; do
        if [ -f "$jar" ]; then
            CLASSPATH="$CLASSPATH:$jar"
        fi
    done
    info "Added lib directory JARs to classpath"
fi

# Clean bin directory before compilation
info "Cleaning bin directory"
rm -rf "$BIN_DIR"/*

# Compile all Java files
info "Compiling Java files..."
javac -d "$BIN_DIR" -cp "$CLASSPATH" $JAVA_FILES

# Check compilation status
if [ $? -eq 0 ]; then
    info "Compilation successful! Class files are in $BIN_DIR"
else
    error "Compilation failed"
fi

# Copy any resource files (optional)
info "Copying resource files..."
for dir in $(find "$PROJECT_ROOT" -type d -not -path "*/\.*" -not -path "*/bin/*"); do
    rel_dir=${dir#$PROJECT_ROOT/}
    if [ "$rel_dir" != "$PROJECT_ROOT" ]; then
        mkdir -p "$BIN_DIR/$rel_dir"
        for res in $(find "$dir" -maxdepth 1 -type f -not -name "*.java" -not -name "*.class"); do
            if [ -f "$res" ]; then
                cp "$res" "$BIN_DIR/$rel_dir/"
            fi
        done
    fi
done

info "Build completed successfully"