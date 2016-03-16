#JFLAGS = -g # flags
CP = .
CPFLAG = -cp $(CP)
JC = javac # java compiler
JRE = java # JRE
EXEC = Main #executable file
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $(CPFLAG) $*.java

CLASSES = Main.java Account.java Transaction.java TransactionIO.java AccountIO.java

default: classes

classes: $(CLASSES:.java=.class)

run:
	$(JRE) $(CPFLAG) $(EXEC)
clean:
	$(RM) *.class
