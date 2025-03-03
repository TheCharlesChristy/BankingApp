# Make sure the bin directory is empty
echo -e "\033[1;33mCleaning bin directory...\033[0m"
if [ -d bin ]; then
    for entry in bin/*; do
        [ -e "$entry" ] || continue
        echo -e "\033[1;31mRemoving $(realpath "$entry")\033[0m"
        rm -rf "$entry"
    done
fi