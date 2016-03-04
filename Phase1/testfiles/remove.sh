for i in {00..10};
do
	cd $i
	for f in *.outx;
	do
		rm $f
	done;
	cd ../
done;