f = open('Santiago.txt','r')
i = 1
o = 1
t = 1
inoutflag = 0
c = 8
flag = False
transaction = ["withdrawal\n","Transfer\n","Paybill\n","Deposit\n"]
code = [01,02,03,04]
for line in f:
	for x in range (0,4):
		if line == transaction[x]:
			c = code[x]
			i = 1
			o = 1
			t = 1
	infilename =  "%02d"%c+"_"+"%03d"%i+".in"
	outfilename = "%02d"%c+"_"+"%03d"%o+".out"
	tranfilename = "%02d"%c+"_"+"%03d"%t+".trf"
	if line == "	Input file:\n":
		infile = open(infilename,'w')
		inoutflag = 0
		i = i + 1
		flag = True
	elif line == "	Output file:\n":
		outfile = open(outfilename,'w')
		inoutflag = 1
		o = o + 1
		flag = True
	elif flag:
		if inoutflag == 0:
			if line == "		logout\n":
				infile.write(line[2:-1])
				infile.close()
				flag = False
			else:
				infile.write(line[2:])
		elif inoutflag == 1:
			if line == "		You have successfully logged out\n":
				outfile.write(line[2:-1])
				outfile.close()
				flag = False
			else:
				outfile.write(line[2:])