f = open('Disable.txt','r')
i = 1
o = 1
t = 1
inoutflag = 0
c = 7
flag = False
for line in f:
	infilename =  "%02d"%c+"_"+"%03d"%i+".in"
	outfilename = "%02d"%c+"_"+"%03d"%o+".out"
	tranfilename = "%02d"%c+"_"+"%03d"%t+".trf"
	if line == "		Input file:\n":
		infile = open(infilename,'w')
		inoutflag = 0
		i = i + 1
		flag = True
	elif line == "		Expected output:\n":
		infile.close()
		outfile = open(outfilename,'w')
		inoutflag = 1
		o = o + 1
		flag = True
	elif line == "		Output file:\n":
		outfile.close()
		tranfile = open(tranfilename,'w')
		inoutflag = 2
		t = t + 1
		flag = True
	elif line == "		Expected outcome:\n":
		tranfile.close()
		flag = False
	elif flag:
		if inoutflag == 0:
			infile.write(line[3:])
		elif inoutflag == 1:
			if line == "			You have successfully logged out\n":
				outfile.write(line[3:-1])
			else:
				outfile.write(line[3:])
		elif inoutflag == 2:
			if line[3:5] == "00":
				tranfile.write(line[3:-1])
			else:
				tranfile.write(line[3:])