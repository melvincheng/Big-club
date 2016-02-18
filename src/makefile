OBJS = Account.o main.o session.o
CC = clang++
CFLAGS = -Wall -Wextra -c
LFLAGS =

main : $(OBJS)
	$(CC) $(LFLAGS) $(OBJS) -o main.exe

clean:
		\rm *.o main.exe
