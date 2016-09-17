CC = g++
CFLAGS = -Wall -std=c++11

TrieTest: TrieTest.cpp
	$(CC) $(CFLAGS) -o TrieTest TrieTest.cpp Trie.o

TrieTest.cpp: Trie.o
	$(CC) $(CFLAGS) -c TrieTest.cpp

Trie.o: Trie.cpp
	$(CC) $(CFLAGS) -c Trie.cpp Trie.h

clean:
	rm TrieTest *.o

test:
	./TrieTest
