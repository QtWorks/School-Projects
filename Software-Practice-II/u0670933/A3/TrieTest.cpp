/*
Author: Tanner Barlow
u0670933
CS 3505
January 30, 2016
*/
#include "stdafx.h"
#include "Trie.h"
#include <iostream>
#include <fstream>

using namespace std;
using namespace A3;

void lookupTest(string dictionary, string lookup) {
	Trie* trie = new Trie();

	ifstream addFile(dictionary);
	ifstream checkFile(lookup);

	string line;
	if (addFile.is_open()) {
		while (getline(addFile, line)) {
			//do stuff with line
			trie->addWord(line);
		}
	}
	else {
		cout << "Unable to open file: " << dictionary << endl;
		return;
	}
	if (checkFile.is_open()) {
		while (getline(checkFile, line)) {
			//do stuff with line
			bool isWord = trie->isWord(line);
			if (isWord){
				cout << line << " is found" << endl;;
			}
			else {
				cout << line << " is not found, did you mean: " << endl;
				vector<string> words = trie->allWordsWithPrefix(line);
				if (words.size() > 0) {
					for (vector<string>::iterator it = words.begin(); it != words.end(); it++) {
						cout << "   " << *it << endl;
					}
				}
				else {
					cout << "   no alternatives found" << endl;
				}
			}
		}
	}
	else {
		cout << "Unable to open file: " << lookup << endl;
		return;
	}
}

int main(int argc, char **argv) {
	if (argc != 3) {
		cout << "Usage: " << endl;
		cout << "./TrieTest  dictionary.txt  lookup.txt" << endl;
		return 0;
	}
	string dictionary = argv[1];
	string lookup = argv[2];

	lookupTest(dictionary,lookup);

	return 1;
}
