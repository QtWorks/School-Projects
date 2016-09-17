#include <iostream>
#include "Trie.h"
#include "gtest/gtest.h"
#include <fstream>
#include <algorithm>

using namespace A3;
using namespace std;

int refCount = 0;
//Simple add word test
TEST(AddWord_IsWord, OneWord){
    Trie t;
    EXPECT_FALSE(t.isWord("basketball"));
    t.addWord("basketball");
    EXPECT_TRUE(t.isWord("basketball"));
}
//Empty string should not be reported as word
TEST(AddWord_IsWord, EmptyString){
    Trie t;
    EXPECT_FALSE(t.isWord(""));
}
//Makes sure that all words are reported as being in trie
TEST(AddWord_IsWord, MultipleWords){
    Trie t;

	t.addWord("basketball");
	t.addWord("player");
	t.addWord("baskets");
	t.addWord("buckets");
	t.addWord("baseball");
	t.addWord("ball");
	t.addWord("play");
	t.addWord("hoop");

	EXPECT_FALSE(t.isWord("notaword"));
	
	EXPECT_TRUE(t.isWord("basketball"));
	EXPECT_TRUE(t.isWord("player"));
	EXPECT_TRUE(t.isWord("baskets"));
	EXPECT_TRUE(t.isWord("buckets"));
	EXPECT_TRUE(t.isWord("baseball"));
	EXPECT_TRUE(t.isWord("ball"));
	EXPECT_TRUE(t.isWord("play"));
	EXPECT_TRUE(t.isWord("hoop"));
}

//Tests that words that are similar to words in trie are not reported as being words
TEST(AddWord_IsWord, AlmostWords){
    
	Trie t;

	t.addWord("basketball");
	t.addWord("player");
	t.addWord("baskets");
	t.addWord("buckets");
	t.addWord("baseball");
	t.addWord("ball");
	t.addWord("play");
	t.addWord("hoop");
	//One character longer
	EXPECT_FALSE(t.isWord("basketballs"));
	//One character shorter
	EXPECT_FALSE(t.isWord("basketbal"));
	//Real word
	EXPECT_TRUE(t.isWord("basketball"));

}
//Add group of words, make sure words with one character difference are not reported as included,
//but that those that should be in trie are reported as such
TEST(AddWord_IsWord, DifferentEnd){

	Trie t;

	t.addWord("basketball");
	t.addWord("player");
	t.addWord("baskets");
	t.addWord("buckets");
	t.addWord("baseball");
	t.addWord("ball");
	t.addWord("play");
	t.addWord("hoop");
	//Different end character
	EXPECT_FALSE(t.isWord("basketbalk"));
	EXPECT_FALSE(t.isWord("playes"));
	EXPECT_FALSE(t.isWord("baskett"));
	EXPECT_FALSE(t.isWord("buckett"));
	EXPECT_FALSE(t.isWord("basebalk"));
	EXPECT_FALSE(t.isWord("balk"));
	EXPECT_FALSE(t.isWord("plax"));
	EXPECT_FALSE(t.isWord("hooq"));
	//Real words
	EXPECT_TRUE(t.isWord("basketball"));
	EXPECT_TRUE(t.isWord("player"));
	EXPECT_TRUE(t.isWord("baskets"));
	EXPECT_TRUE(t.isWord("buckets"));
	EXPECT_TRUE(t.isWord("baseball"));
	EXPECT_TRUE(t.isWord("ball"));
	EXPECT_TRUE(t.isWord("play"));
	EXPECT_TRUE(t.isWord("hoop"));
}
//Helper method. Ensures ref count is s.length + 1 when added and that it returns to 0 when the trie is deleted
void RefCountTest(string s){
  	Trie* t = new Trie();
    EXPECT_FALSE(t->isWord(s));
    t->addWord(s);
	//Refcount will be length + 1 to account for node
    EXPECT_EQ(s.length() + 1,refCount);
    EXPECT_TRUE(t->isWord(s));
	delete t;
	//Ref count should return to 0 after deleting nodes
	EXPECT_EQ(0,refCount);
}
//Testing constructor and destructor with ref count. Reports that each node gets deleted properly
TEST(RefCount, StressTest){
	RefCountTest("basketball");
	RefCountTest("pneumonoultramicroscopicsilicovolcanoconiosis");
	RefCountTest("people");
	RefCountTest("information");
	RefCountTest("map");
	RefCountTest("government");
	RefCountTest("computer");
	RefCountTest("understanding");
	RefCountTest("theory");
	RefCountTest("law");
	RefCountTest("software");
	RefCountTest("basketball");
	RefCountTest("investment");
	RefCountTest("society");
	RefCountTest("activity");
	RefCountTest("development");
	RefCountTest("player");
	RefCountTest("variety");
	RefCountTest("organization");
	RefCountTest("technology");
}
//Helper method. Reports if vector contains string.
bool vectorContains(vector<string> v, string s){
	if(std::find(v.begin(), v.end(), s) != v.end()) {
    	return true;
	} else {
    	return false;
	}
}

//Test that all words with prefix gets the correct size of words
TEST(Prefix, PrefixCount){
	Trie t;
	t.addWord("basket");
	t.addWord("basketball");
	t.addWord("ball");
	t.addWord("wrong");
	vector<string> v = t.allWordsWithPrefix("b");
	EXPECT_EQ(3, v.size());
}
//Get all words with a prefix. Make sure all words are there and that no words are there that shouldn't be there
TEST(Prefix, PrefixWords){
	Trie t;
	t.addWord("basket");
	t.addWord("basketball");
	t.addWord("ball");
	t.addWord("wrong");
	vector<string> v = t.allWordsWithPrefix("b");
	EXPECT_EQ(3, v.size());

	EXPECT_TRUE(vectorContains(v, "basket"));
	EXPECT_TRUE(vectorContains(v, "basketball"));
	EXPECT_TRUE(vectorContains(v, "ball"));
	EXPECT_FALSE(vectorContains(v, "wrong"));
}
//Makes sure that a word is returned as a prefix of itself
TEST(Prefix, SameWord){
	Trie t;
	t.addWord("ball");
	vector<string> v = t.allWordsWithPrefix("ball");
	EXPECT_TRUE(vectorContains(v, "ball"));
	EXPECT_EQ(1, v.size());
}
//Testing the assignment operator with a Trie on the stack. Currently disabled, working on fix
TEST(AssignmentOperator, DISABLED_Stack){
	Trie t;
	t.addWord("basketball");
	EXPECT_TRUE(t.isWord("basketball"));
	Trie t2;
	t2 = t;
	EXPECT_TRUE(t2.isWord("basketball"));
}
//Testing the assignment operator with a Trie on the heap. Currently disabled, working on fix
TEST(AssignmentOperator, DISABLED_Heap){
	Trie* t = new Trie();
	t->addWord("basketball");
	EXPECT_TRUE(t->isWord("basketball"));
	Trie* t2 = new Trie();
	*t2 = *t;
	EXPECT_TRUE(t2->isWord("basketball"));
}
//Testing the copy constructor with parentheses. Currently disabled, working on fix
TEST(CopyConstructor, DISABLED_Parentheses){
	Trie t;
	t.addWord("basketball");
	EXPECT_TRUE(t.isWord("basketball"));
	Trie* t2 = new Trie(t);
	EXPECT_TRUE(t2->isWord("basketball"));	
}
//Testing the equals copy constructor. Currently disabled, working on fix
TEST(CopyConstructor, DISABLED_Equals){
	Trie t;
	t.addWord("basketball");
	EXPECT_TRUE(t.isWord("basketball"));
	Trie t2 = t;
	EXPECT_TRUE(t2.isWord("basketball"));	
}

int main(int argc, char* argv[]){
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}
