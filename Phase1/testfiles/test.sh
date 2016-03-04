for i in {00..10};
do
	cd $i
	for f in *.in;
	do
		printf "\n"$f"\n"
		../../../src/bank.exe < $f
		# echo "${f%.*}"
	done;
	cd ../
done;