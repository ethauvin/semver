#!/bin/bash

if [ $# -eq 0 ]; then
    echo "Usage: $0 <arg> [...]"
    exit 1
fi

dir=$(dirname "$(readlink -f "$0")")
cyan=$(tput setaf 6)
normal=$(tput sgr0)

for ex in "java" "kotlin"; do
    cd "$dir/$ex" || exit 1
    echo "> Project: ${cyanlor}${ex}${normal}"
    ./gradlew --console=plain --no-build-cache clean "$@" || exit 1
    echo
done
