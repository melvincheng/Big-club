for i in {00..10};
do
	cd $i
	for f in *.out;
	do
		cat $f
		grep "poop" -q $f && echo "FOUND"
		# sed - 's/Welcome/Welcome/' -e t -e 's/Please enter a transaction:\nPlease select the following:\nadmin\nuser/Welcome\nPlease enter a transaction:\nPlease select the following:\nadmin\nuser/' $f
	done;
	cd ../

done;