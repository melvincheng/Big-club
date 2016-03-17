JAVAC=javac -d bin -classpath bin
sources = src/Account.java\
 src/Transaction.java\
 src/TransactionIO.java\
 src/AccountIO.java\
 src/TransactionProcessor.java\
 src/Bank.java

classes = $(sources:.java=.class)

all: $(classes)

run:
	java -cp ./bin Bank

clean :
	rm -f bin/*.class
	rm -f src/*.class

%.class : %.java
	$(JAVAC) $<
