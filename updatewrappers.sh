#!/bin/sh

echo "semver"
gradle -q wrapper
kobaltw --update --log 0
cp -f /c/kobalt/kobalt/wrapper/kobalt-wrapper.jar /k/java/semver/kobalt/wrapper
./kobaltw --version
cd examples/java
echo
echo "examples/java"
gradle -q wrapper
kobaltw --update --log 0
cp -f /c/kobalt/kobalt/wrapper/kobalt-wrapper.jar /k/java/semver/kobalt/wrapper
./kobaltw --version
echo
echo "examples/kotlin"
cd ../kotlin
gradle -q wrapper 
cd ..
