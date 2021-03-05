#!/bin/sh

prg="$0"
while [ -h "$prg" ]; do
    new_prg=$(/bin/ls -ld "$prg")

    new_prg=$(expr "$new_prg" : ".* -> \(.*\)$")
    if expr "x$new_prg" : 'x/' >/dev/null; then
        prg="$new_prg"
    else
        jar_dir="${prg%/*}"
        prg="$jar_dir/$new_prg"
    fi
done

old_pwd=$(pwd)
jar_dir="${prg%/*}"
cd "$jar_dir" || exit 1
jar_dir=$(pwd)
cd "$old_pwd" || exit 1

javaOpts="-Dfile.encoding=utf-8"

exec java $javaOpts -jar "$jar_dir"/PdfMetadaEditor.jar "$@"
