// This is the main DLL file.
/*
	Author: Tanner Barlow
	u0670933
	CS 3505
	January 30, 2016
*/

#include "stdafx.h"
#include "Trie.h"
#include <stdio.h>
#include <iostream>
#include <ctype.h>
using namespace std;
using namespace A3;
//Initializes a root node for the trie with an empty space as its character
Trie::Trie()
{
	root = new Node();
}
//Deletes a trie
Trie::~Trie()
{
	delete root;
}
//Deep copy of trie
Trie::Trie(const Trie & other)
{
	if(other.root != nullptr)
		root = new Node(*other.root);	
}
//Assignment operator override
Trie & Trie::operator=(Trie other)
{
	swap(root, other.root);
	return *this;
}
//Adds a word to the trie
void Trie::addWord(string s)
{
	addWordRecursive(root, s);
}
//Adds all words in a vector to trie
void Trie::addAllWords(vector<string> v)
{
	for (vector<string>::iterator it = v.begin(); it != v.end(); it++) {
		addWord(*it);
	}
}
//Recursively adds a word to a node. 
void Trie::addWordRecursive(Node* n, string s)
{
	if (n == nullptr)
		return;
	//base case
	if (s.size() < 1) {
		n->isWord = true;
		return;
	}
	//normalize character
	char first = tolower(s[0]);
	//get pointer for child with character
	Node* child_ptr = n->getChild(first);
	//create a new node if child is null
	if(child_ptr == nullptr){
		child_ptr = new Node();
		n->setChild(child_ptr, first);
	}
	//make recursive call
	addWordRecursive(child_ptr, s.substr(1));
}
//Reports if word is found in trie
bool Trie::isWord(string s)
{
	//empty string should be false
	if (s.size() < 1)
		return false;
	return isWordRecursive(root, s);
}
//Reports if all words are found in trie
bool Trie::areAllWords(vector<string> v)
{
	//iterate through vector. If a word is found that is not in trie, return false
	for (vector<string>::iterator it = v.begin(); it != v.end(); it++) {
		if (!isWord(*it))
			return false;
	}
	//otherwise, return true
	return true;
}
//Recursive method 
bool Trie::isWordRecursive(Node* n, string s)
{
	//get pointer for node at string
	Node* node_ptr = getToNode(n, s);
	//if pointer is not null, string exists
	//if pointer is word, return true
	if (node_ptr != nullptr)
		if(node_ptr->isWord)
			return true;
	//if pointer is null or pointer is not word, return false
	return false;
}
//Returns vector of all words with prefix of s
vector<string> Trie::allWordsWithPrefix(string s)
{
	//initialize vector
	vector<string>* v = new vector<string>();
	//get to node of string s
	Node* node = getToNode(root, s);
	//if pointer is null, return empty vector
	if (node == nullptr)
		return vector<string>();
	//node is not null. if it's a word, add to vector
	if (node->isWord)
		v->push_back(s);
	//put all words in node's tree in the vector
	allWordsInNode(node, v, s);
	//return vector
	return *v;
}
string Trie::toString()
{
	char root_char = '*';
	string initial_indent = "";
	return root->printTree(root, root_char, initial_indent);
}
//Puts all words in n's tree into vector v. Does not include root even if the root is a word.
void Trie::allWordsInNode(Node * n, vector<string>* v, string charsSoFar)
{
	//iterate through all chilren
	for (char c = 'a'; c <= 'z'; c++) {
		Node* child_ptr = n->getChild(c);
		//if child is not null, add letter to chars so far
		if (child_ptr != nullptr) {
			string temp = charsSoFar + c;
			//if child makes a word, add word to vector
			if (child_ptr->isWord)
				v->push_back(temp);
			//make recursive call on child's tree
			allWordsInNode(child_ptr, v, temp);
		}
	}
}
//Returns pointer of node for string s. Returns nullptr if string is not found
Node * Trie::getToNode(Node* n, string s)
{
	//base case is an empty string
	if (s.size() < 1) {
		return n;
	}
	//
	else {
		//normalize character
		char first = tolower(s[0]);
		//get pointer of first character in string
		Node* child_ptr = n->getChild(first);
		//return nullptr if string is not found
		if (child_ptr == nullptr)
			return child_ptr;
		//otherwise, make recursive call on child's tree
		else
			return getToNode(child_ptr, s.substr(1));
	}
}
//Assignment operator for node
Node & Node::operator=(Node other)
{
	swap(isWord, other.isWord);
	swap(children, other.children);
	return *this;
}
//Constructs node with letter value. Initializes children array to null pointers
Node::Node() : children()
{
	isWord = false;
	directChildren = 0;
	refCount++;
}
Node::Node(const Node & other)
{
	for (size_t i = 0; i < 26; i++) {
		children[i] = new Node(*other.children[i]);
	}
	directChildren = other.directChildren;
}
//Deletes itself and all nodes
Node::~Node()
{
	deleteTree(this);
	refCount--;
}
//Deletes all nodes in the tree of a node pointer
void Node::deleteTree(Node* n)
{
	for (char i = 'a'; i <= 'z'; i++) {
		if (n->getChild(i) != nullptr) {
			delete n->getChild(i);
		}
	}
}
//Returns the node pointer with value 'c'
Node* Node::getChild(char c)
{
	return children[getIndex(c)];
}
//Sets node pointer as a child at the correct place
void Node::setChild(Node* child, char c)
{
	unsigned int index = getIndex(c);
	children[index] = child;
	directChildren++;
}
//Assumes that characters are already lower case
int Node::getIndex(char c)
{
	return (int)(c - 'a');
}
//Returns string of hierarchical tree for a given node
string Node::printTree(Node * n, char node_char, string indent)
{
	string result = indent + node_char + '\n';
	if (n != nullptr) {
		for (char c = 'a'; c <= 'z'; c++) {
			Node* child = n->getChild(c);
			if (child != nullptr) {
				result += printTree(child, c, indent + "  ");
			}
		}
		return result;
	}
	return "";
}
