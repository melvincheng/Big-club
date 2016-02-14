f = open('createdelete.txt','r')
i = 1
o = 1
t = 1
inoutflag = 0
c = 5
flag = False
templine = ""
transaction = ["Create\n","delete\n"]
code = [05,06]
for line in f:
	for x in range (0,2):
		if line == transaction[x]:
			c = code[x]
			i = 1
			o = 1
			t = 1
	infilename =  "%02d"%c+"_"+"%03d"%i+".in"
	outfilename = "%02d"%c+"_"+"%03d"%o+".out"
	tranfilename = "%02d"%c+"_"+"%03d"%t+".trf"
	# print(line[:-1])
	if line == "			Input File\n":
		infile = open(infilename,'w')
		inoutflag = 0
		i = i + 1
		flag = True
	elif line == "			Expected Output:\n":
		infile.close()
		outfile = open(outfilename,'w')
		inoutflag = 1
		o = o + 1
		flag = True
	elif line == "			Transaction Output:\n":
		outfile.close()
		tranfile = open(tranfilename,'w')
		inoutflag = 2
		t = t + 1
		flag = True
	elif flag:
		if inoutflag == 0:
			infile.write(line[4:])
		elif inoutflag == 1:
			if line == "				You have successfully logged out\n":
				outfile.write(line[4:-1])
			else:
				outfile.write(line[4:])
		elif inoutflag == 2:
			if line[4:6] == "00":
				tranfile.write(line[4:-1])
				tranfile.close()
				flag = False
			else:
				tranfile.write(line[4:])
