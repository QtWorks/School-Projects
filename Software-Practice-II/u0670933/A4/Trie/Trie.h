// Trie.h
/*
	Author: Tanner Barlow
	u0670933
	CS 3505
	January 30, 2016
*/

#ifndef TRIE_H
#define TRIE_H

#include <vector>
#include <string>

using namespace std;

extern int refCount;

namespace A3 {

	class Node {
		//array of children
		Node* children[26];
		//how many direct children a node has
		int directChildren;
	public:
		//reports whether a node finishes a word
		bool isWord;
		//Constructs a new node
		Node();
		//Deep copy
		Node(const Node& other);
		//Assignment override
		Node& operator=(Node other);
		//Destructor for node
		~Node();
		//Gets pointer for child with letter c
		Node* getChild(char c);
		//Sets a node as child of current node at position c
		void setChild(Node* child, char c);
		//String to print a node's tree
		string printTree(Node* n, char node_char, string indent);
	private:
		//Recursively deletes a tree of nodes
		void deleteTree(Node* n);
		//Gets the index in which node belongs in array
		int getIndex(char c);
	};


	class Trie
	{
		Node* root;
	public:
		//Constructor
		Trie(); 
		//Destructor
		~Trie();
		//Deep copy
		Trie(const Trie& other); 
		//Assignment operator override
		Trie& operator=(Trie other);
		//Add a word to the trie
		void addWord(string s);
		//Add a vector of words to trie
		void addAllWords(vector<string> v);
		//Reports whether word is found in trie
		bool isWord(string s);
		//Reports whether vector of words are all found in trie
		bool areAllWords(vector<string> v);
		//Returns a vector of words with prefix of string s
		vector<string> allWordsWithPrefix(string s);
		//Returns string of entire trie
		string toString();
	private:
		//Recursively adds words to a trie
		void addWordRecursive(Node* n, string s);
		//Recursively reports whether word is found in trie
		bool isWordRecursive(Node* n, string s);
		//Fills vector with all words in a given node's tree
		void allWordsInNode(Node* n, vector<string>* v, string charsSoFar);
		//Returns pointer for node with given string
		Node* getToNode(Node* n, string s);
	};
}
#endif
