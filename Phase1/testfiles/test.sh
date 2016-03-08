#!/bin/bash
#if a test passes, no output is produced


#for loop to iterate through the directories that contain the test files
for directory in {00..10};
do
	#for loop to iterate through the input files in the directory
	for inputFile in $directory/*.in;
	do
		#output which test is running
		#extract the filename without the path
		fileName=$(basename "$inputFile")

		#outputs just the file name for test indication
		echo ${fileName%.*}

		#runs the program
		#first argument is the current accounts file
		#seconds argument is the outputed transaction file
		#inputs the inputfile
		#outputs an output file
		../../src/bank.exe $1 ${fileName%.*}.trfx < $inputFile > ${fileName%.*}.outx

		#checks if file is created from the bank
		#if file is not created, then create an empty file
		#used for when test cases do not login
		if [ ! -f ./${fileName%.*}.trfx ]; then
			echo -n "" > ${fileName%.*}.trfx
		fi
		# 	echo -n "" > "${inputFile%.*}.trfx"
		#checks for differences between the expected output file and actual output file
		#-y outputs two columns, left side is the first file, the right side is the second file
		#suppress common lines removes all lines that are matching between the files
		mv -f ./${fileName%.*}.trfx ./$directory
		mv -f ./${fileName%.*}.outx ./$directory

		#move into directory to check for files
		cd $directory

		#Z ignores all trailing whitespaces
		#checks for differences between the expected output file and actual output file
		diff -yZ --suppress-common-lines ${fileName%.*}.out ${fileName%.*}.outx

		#checks for differences between the expected transaction file and actual transaction file
		diff -yZ --suppress-common-lines ./${fileName%.*}.trf ./${fileName%.*}.trfx

		#exit directory
		cd ..

		#newline inorder for all testcase to have their own block
		echo ""
	done;
done;
