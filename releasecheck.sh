#!/bin/bash

pwd=$PWD
red=$(tput setaf 1)
cyan=$(tput setaf 6)
normal=$(tput sgr0)
date=$(date +%Y)

sonar="sonarqube"
doc="pandoc"
src="src/main/java/net/thauvin/erik/semver"
test="src/test/java/net/thauvin/erik/semver"
ext=".java"

checkCopyright() {
    if [ "$(grep -c "$date" "$1")" -eq 0 ]; then
        echo -e Copyright: "${red}$f${normal}"
    else
        echo -e Checked: "$1"
    fi
}

runGradle() {
    echo -e "${cyan}${1}${normal}"
    cd "$1" || exit 1
    shift
    ./gradlew --console=plain --no-build-cache clean $@ || exit 1
    cd "$pwd"
    read -p "Press enter to continue..."
    clear
}

./updatewrappers.sh
read -p "Press enter to continue..."
clear

gradle --console=plain --no-build-cache clean dU check $doc $sonar || exit 1
read -p "Press enter to continue..."
clear

runGradle examples/java run
runGradle examples/kotlin run runJava
clear

cd "$pwd"

for f in LICENSE.TXT ${src}/*${ext} ${test}/*${ext} ; do
    checkCopyright "$f"
done
