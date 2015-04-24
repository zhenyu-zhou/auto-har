#!/bin/bash

cd /Users/zzy/Documents/eclipse/WPT/bin
f1="$1"
f2="$2"
out="$3"
rm "${out}/${f1}_${f2}"
java -cp .:/Users/zzy/Downloads/java-json.jar wpt.HARReader $f1 $f2 $out
