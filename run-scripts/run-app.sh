#!/bin/bash
# Ensure bin directory exists
mkdir -p bin
# Compile all Java sources from lib and src into bin
javac -d bin lib/*.java src/*.java
# Run the main app using the bin directory as classpath
java -cp bin BankingApp
#!/bin/bash
# Create build directory if not exists
echo -e "\033[1;33mCreating build directory...\033[0m"
mkdir -p bin

# Make sure the bin directory is empty
echo -e "\033[1;33mCleaning bin directory...\033[0m"
if [ -d bin ]; then
    for entry in bin/*; do
        [ -e "$entry" ] || continue
        echo -e "\033[1;31mRemoving $(realpath "$entry")\033[0m"
        rm -rf "$entry"
    done
fi

# Compile App.java from project root
echo -e "\033[1;32mCompiling App.java...\033[0m"
javac -d bin App.java

# Compile the Test.java file for testing
echo -e "\033[1;32mCompiling Test.java...\033[0m"
javac -d bin Test.java

# Recursively compile all Java files in src
echo -e "\033[1;32mCompiling source files...\033[0m"
find ./src -type f -name "*.java" | while read -r file; do
    echo -e "\033[1;36mCompiling $(basename "$file")...\033[0m"
    javac -d bin "$file"
done

# Recursively compile all Java files in lib
echo -e "\033[1;32mCompiling library files...\033[0m"
find ./lib -type f -name "*.java" | while read -r file; do
    echo -e "\033[1;36mCompiling $(basename "$file")...\033[0m"
    javac -d bin "$file"
done

# Recursively compile all Java files in test
echo -e "\033[1;32mCompiling test files...\033[0m"
find ./tests -type f -name "*.java" | while read -r file; do
    echo -e "\033[1;36mCompiling $(basename "$file")...\033[0m"
    javac -d bin "$file"
done

# Run the tests script
echo -e "\033[1;33mRunning tests...\033[0m"
java -cp bin Test

# Run the main app
echo -e "\033[1;33mRunning application...\033[0m"
java -cp bin App