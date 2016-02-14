#!/bin/bash
for i in {1..10}
do
  trans="0$i"
  mkdir $trans
  for j in {1..10}
  do
    cp transtemplate.tpl "$trans"/"$trans"_"$j".trf
  done
done
