#!/bin/sh

pandoc --from gfm \
--to html5 \
--metadata "pagetitle=Semantic Version Annotation Processor" \
-s \
-c docs/github-pandoc.css \
-o docs/README.html \
README.md
