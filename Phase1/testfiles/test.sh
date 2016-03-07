for i in {00..10};
do
	for f in $i/*.in;
	do
		printf "\n${f%.*}\n"
		../../src/bank.exe < $f > "${f%.*}.outx"

		diff -y --suppress-common-lines "${f%.*}.out" "${f%.*}.outx"
		printf "\n"
		# rm "${f%.*}.trfx"
		mv -f ./transactions.trf ./"${f%.*}.trfx"
		diff -yZ --suppress-common-lines "${f%.*}.trf" "${f%.*}.trfx"
	done;
done;