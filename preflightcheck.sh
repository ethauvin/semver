#!/bin/bash

#
# Version: 1.1
#

# set source and test locations
src="src/main/java/net/thauvin/erik/semver"
test="src/test/java/net/thauvin/erik/semver"
# e.g: .java, .kt, etc.
ext=".java"
java8=true
# e.g: <example directory> <gradle args...>
declare -a examples=(
    "examples/java run"
    "examples/kotlin run runJava")
# e.g: empty or javadoc, etc.
doc="javadoc pandoc"
# e.g. empty or sonarqube
sonar="sonarqube"

# gradle default command line args
opts="--console=plain --no-build-cache --no-daemon"

###

if [ "$java8" = true ]
then
    export JAVA_HOME="$JAVA8_HOME"
    export PATH="$(cygpath "$JAVA_HOME")/bin:$PATH"
fi

pwd=$PWD
red=$(tput setaf 1)
cyan=$(tput setaf 6)
std=$(tput sgr0)
date=$(date +%Y)

pause() {
  read -p "Press [Enter] key to continue..."
}

checkCopyright() {
    if [ "$(grep -c "$date" "$1")" -eq 0 ]
    then
        echo -e "   Invalid: ${red}$f${std}"
    else
        echo -e "   Checked: $1"
    fi
}

runGradle() {
    cd "$1" || exit 1
    clear
    echo -e "> Project: ${cyan}${1}${std} [Gradle]"
    shift
    ./gradlew $opts clean $@ || exit 1
    pause
    cd "$pwd"
}

runKobalt() {
    cd "$1" || exit 1
    if [ -f kobalt/src/Build.kt ]
    then
        clear
        echo -e "> Project: ${cyan}${1}${std} [Kobalt]"
        shift
        ./kobaltw clean $@ || exit 1
        pause
    fi
    cd "$pwd"
}

runMaven() {
    cd "$1" || exit 1
    if [ -f pom.xml ]
    then
        clear
        echo -e "> Project: ${cyan}${1}${std} [Maven]"
        mvn clean compile exec:java || exit 1
        pause
    fi
    cd "$pwd"
}

updateWrappers() {
    clear
    ./updatewrappers.sh
    pause
}

checkDeps() {
    clear
    echo -e "${cyan}Checking depencencies...${std}"
    gradle --console=plain dU || exit 1
    read -p "Check Examples depencencies? [y/n] " cont
    clear
    case $cont in
        [Yy] )  for ex in "${examples[@]}"
                do
                    runGradle $(echo "$ex" | cut -d " " -f 1) dU
                    runKobalt $(echo "$ex" | cut -d " " -f 1) checkVersions
                    runMaven $(echo "$ex" | cut -d " " -f 1) versions:display-dependency-updates 
                    read -p "Continue? [y/n]: " cont
                    clear
                    case $cont in
                        [Yy] ) continue ;;
                        * ) return ;;
                    esac
                done ;;
        * ) return ;;
    esac
}

gradleCheck() {
    clear
    echo -e "${cyan}Checking Gradle build....${std}"
    gradle $opts clean check $doc $sonar || exit 1
    pause
}

runExamples() {
    for ex in "${examples[@]}"
    do
        runGradle $ex
        runKobalt $ex
        runMaven $ex
        read -p "Continue? [y/n]: " cont
        clear
        case $cont in
            [Yy] ) continue ;;
            * ) return ;;
        esac
    done
}

examplesMenu() {
    clear
    echo -e "${cyan}Examples${std}"
    for ex in "${!examples[@]}"
    do
        printf  '    %d. %s\n' $(($ex + 1)) $(echo "${examples[ex]}" | cut -d " " -f 1)
    done
    echo "    $((${#examples[@]} + 1)). Run All Examples"
    read -p "Enter choice [1-${#examples[@]}]: " choice
    clear
    case $choice in
        [0-9] ) if [ "$choice" -gt "${#examples[@]}" ]
                then
                    runExamples
                    examplesMenu
                else
                    runGradle ${examples[$(($choice - 1))]}
                    runKobalt ${examples[$(($choice - 1))]}
                    runMaven ${examples[$(($choice - 1))]}
                    examplesMenu
                fi ;;
        * ) return ;;
    esac
}

validateCopyrights() {
    clear
    echo -e "${cyan}Validating copyrights...${std}"
    for f in LICENSE.TXT ${src}/*${ext} ${test}/*${ext}
    do
        checkCopyright "$f"
    done
    pause
}

everything() {
    updateWrappers
    checkDeps
    gradleCheck
    runExamples
    validateCopyrights
}

showMenu() {
    clear
    echo "${cyan}Preflight Check${std}"
    echo "    1. Update Wrappers"
    echo "    2. Check Dependencies"
    echo "    3. Check Gradle Build"
    echo "    4. Run Examples"
    echo "    5. Validate Copyrights"
    echo "    6. Check Everything"
}

readOptions(){
	local choice
	read -p "Enter choice [1-6]: " choice
	case $choice in
		1) updateWrappers ;;
		2) checkDeps ;;
        3) gradleCheck ;;
        4) examplesMenu ;;
        5) validateCopyrights ;;
        6) everything ;;
		*) exit 0 ;;
	esac
}

while true
do
	showMenu
	readOptions
done
