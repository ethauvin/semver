#!/bin/bash

# Version 1.0

if [ $# -eq 0 ]; then
  echo "Usage: $0 <arg ...>"
  exit 1
fi

# set the examples directories
declare -a examples=(
  "java/bld"
  "java/gradle"
  "kotlin")

dir=$(dirname "$(readlink -f "$0")")
cyan=$(tput setaf 6)
normal=$(tput sgr0)

i=0
for ex in "${examples[@]}"; do
  if [ $i -ne 0 ]; then
    read -p "Press [Enter] key to continue..."
    clear
  fi
  cd "$dir/$ex" || exit 1
  echo "> Project: ${cyan}${ex}${normal}"
  if [ -x "bld" ]; then
    ./bld compile "$@" || exit 1
  else
    ./gradlew --console=plain --no-build-cache clean "$@" || exit 1
  fi
  ((i++))
done
