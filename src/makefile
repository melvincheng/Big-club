OBJS = Account.o main.o session.o
CXX=clang++
CXXFLAGS = -Wall -Wextra -std=c++11
LFLAGS =

bank : $(OBJS)
	$(CXX) $(CXXFALGS) $(OBJS) -o bank

Account.o : Account.h
session.o : session.h Account.h
main.o : session.h

.PHONY : clean
clean:
		rm bank $(OBJS)
