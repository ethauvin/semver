#!/bin/sh

echo "semver"
gradle -q wrapper
kobaltw --update --log 0
./kobaltw --version
cd examples/java
echo
echo "examples/java"
gradle -q wrapper
kobaltw --update --log 0
./kobaltw --version
echo
echo "examples/kotlin"
cd ../kotlin
gradle -q wrapper 
cd ..
