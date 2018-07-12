#!/bin/bash

DEBUG=false

rm="rm -rf"

if [ "$DEBUG" = true ]; then
	rm="echo rm -rf"
fi

buildkt="kobalt/src/Build.kt"

name=$(cat $buildkt | grep -m 1 "name = " | cut -d"\"" -f 2)
group=$(cat $buildkt | grep -m 1 "group = " | cut -d"\"" -f 2)

if [ -z "$1" ]; then
	version=$(cat $buildkt | grep -m 1 "version = " | cut -d"\"" -f 2)
else
	version="$1"
fi

maven="/k/maven/repository/${group//.//}/${name}/${version}"
kobalt="$HOME/.kobalt/cache/${group//.//}/${name}/${version}"
localRepo="$HOME/.kobalt/localMavenRepo/${group//.//}/${name}/${version}"

read -p "Delete version ${version}? " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
	for dir in "$kobalt" "$maven" "$localRepo"; do
		if [ -d "$dir" ]; then
			echo -e "Deleting : \e[32;1m$dir\e[0m"
			$rm "$dir"
		else
			echo -e "Not Found: \e[31;1m$dir\e[0m"
		fi
	done
fi