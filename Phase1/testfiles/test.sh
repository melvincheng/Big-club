for i in {00..10};
do
	cd $i
	for f in *.in;
	do
		printf "\n"$f"\n"
		../../../src/bank.exe < $f #> "${f%.*}.outx"

		diff -y "${f%.*}.out" "${f%.*}.outx"
		echo "\n"
	done;
	cd ../

done;