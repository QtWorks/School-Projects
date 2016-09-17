CC=g++
CFLAGS=-Wall -std=c++11
TRIEDIR=./Trie
GTESTLIB= libraries/googletest-master/googletest
LINKGTESTLIB= -L $(GTESTLIB)/ -lgtest -lgtest_main -lpthread
INCLUDES= -I $(GTESTLIB)/include/ -I Trie/

GTestTrie: GTestTrie.cpp $(TRIEDIR)/Trie.o
	$(CC) -o GTestTrie GTestTrie.cpp Trie/Trie.o -O2 $(INCLUDES) $(LINKGTESTLIB) -lm -lz
$(TRIEDIR)/Trie.o:
	$(TRIEDIR)/ make
clean:
	rm GTestTrie
test:
	./GTestTrie
