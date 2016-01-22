#!bin/bash
for x in $2/*;
do diff -q $1 $x;
done;