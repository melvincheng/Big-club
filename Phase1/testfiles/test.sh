for i in {00..10};
do
	cd $i
	for f in *.in;
	do
		printf "\n"$f"\n"
	
		../../../src/bank.exe < $f > "${f%.*}.outx"
		diff -y "${f%.*}.out" "${f%.*}.outx"
	done;
	cd ../

done;