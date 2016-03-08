#!/bin/bash

#for loop to iterate through the directories that contain the test files
for directory in {00..10};
do
	#for loop to iterate through the input files in the directory
	for inputFile in $directory/*.in;
	do
		#output which test is running
		#extract the filename without the path
		fileName=$(basename "$inputFile")
		#outputs just the file name
		echo "${fileName%.*}"
		#outputs the
		../../src/bank.exe < $inputFile > "${inputFile%.*}.outx"

		#checks for differences between the expected output file and actual output file
		#-y outputs two columns, left side is the first file, the right side is the second file
		#suppress common lines removes all lines that are matching between the files
		diff -yZ --suppress-common-lines "${inputFile%.*}.out" "${inputFile%.*}.outx"

		#if a transaction file is not produced from the test, create an empty transaction file
		#else rename the transaction file produced into a transaction file result
		#This is requied for tests that do not login to a session
		if [ ! -f ./transactions.trf ]; then
			echo -n "" > "${inputFile%.*}.trfx"
		else
			mv -f ./transactions.trf ./"${inputFile%.*}.trfx"
		fi

		#checks for differences between the expected transaction file and actual transaction file
		#Z ignores all trailing whitespaces
		diff -yZ --suppress-common-lines "${inputFile%.*}.trf" "${inputFile%.*}.trfx"
		#newline inorder for all testcase to have their own block
		echo ""
	done;
done;
