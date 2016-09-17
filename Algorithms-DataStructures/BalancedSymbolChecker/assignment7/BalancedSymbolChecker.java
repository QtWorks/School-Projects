package assignment7;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class containing the checkFile method for checking if the (, [, and { symbols
 * in an input file are correctly matched.
 * 
 * @authors Joshua Callahan & Tanner Barlow
 */
public class BalancedSymbolChecker {

	private static MyStack<Character> stack;
	private static int line;
	private static int column;

	/**
	 * Returns a message indicating whether the input file has unmatched
	 * symbols. (Use the methods below for constructing messages.) Throws
	 * FileNotFoundException if the file does not exist.
	 */

	public static String checkFile(String filename) throws FileNotFoundException {

		stack = new MyStack<Character>();
		// Reads words from a file (assumed to contain one word per line)
		// Returns the words as an array of strings
		ArrayList<String> lines = new ArrayList<String>();
		
		line = 1;
		column = 1;
		
		boolean multiComment = false;
		boolean lineComment = false;
		boolean stringLiteral = false;
		boolean charLiteral = false;
		

		BufferedReader input = new BufferedReader(new FileReader(filename));
		try {
			while (input.ready()) {
				lines.add(input.readLine());
			}
		} catch (IOException e) {
			throw new FileNotFoundException();
		}

		for (String s : lines) {
			column = 1;

			// Each line from the file is iterated through.
			for (int i = 0; i < s.length(); i++) {
				
				char c = s.charAt(i);
				if(i > 0 && (s.charAt(i - 1) == '\\' || (!stringLiteral && !multiComment && !lineComment && s.charAt(i - 1) == '\''))){
					charLiteral = true;
				}
				
				if(!lineComment && !multiComment && !stringLiteral && !charLiteral && c == '/' && i < s.length() - 1 && s.charAt(i+1) == '/'){
					lineComment = true;
				}
					
								
				if(!multiComment && !lineComment && !stringLiteral && c == '/' && i < s.length() - 1 && s.charAt(i+1) == '*'){
					multiComment = true;
				}
					
				
				if(multiComment && c == '*' && i < s.length() - 1 && s.charAt(i+1) == '/'){
					multiComment = false;					
				}
					
				
				if(!stringLiteral && !multiComment && !lineComment && !charLiteral && c == '"'){
					stringLiteral = true;
				}else if(stringLiteral && c == '"' && !charLiteral){
					stringLiteral = false;
				}

				if(!stringLiteral && !multiComment && !lineComment && !charLiteral)
				{
					if(stack.isEmpty() && (c == ')' || c == ']' || c == '}'))
						return unmatchedSymbol(line, column, c, ' ');
					
					if(c == '(' || c == '[' || c == '{')
						stack.push(c);
					
					if(c == ')' || c == ']' || c == '}')
					{
						if(c == matchingSymbol(stack.peek()))
							stack.pop();
						else{
							return unmatchedSymbol(line, column, c, matchingSymbol(stack.peek()));
						}
					}
				}
				column++;
				if(charLiteral)
					charLiteral = false;
			}
			if(lineComment)
			{
				lineComment = false;
			}
			line++;
		}
		if(multiComment)
			return unfinishedComment();
		
		if(!stack.isEmpty())
			return unmatchedSymbolAtEOF(matchingSymbol(stack.peek()));
		else{
			return allSymbolsMatch();
		}


	}

	private static char matchingSymbol(char c) {
		if (c == '(')
			return ')';
		else if (c == '{')
			return '}';
		else 
			return ']';
	}

	/**
	 * Returns an error message for unmatched symbol at the input line and
	 * column numbers. Indicates the symbol match that was expected and the
	 * symbol that was read.
	 */
	private static String unmatchedSymbol(int lineNumber, int colNumber,
			char symbolRead, char symbolExpected) {
		return "ERROR: Unmatched symbol at line " + lineNumber + " and column "
				+ colNumber + ". Expected " + symbolExpected + ", but read "
				+ symbolRead + " instead.";
	}

	/**
	 * Returns an error message for unmatched symbol at the end of file.
	 * Indicates the symbol match that was expected.
	 */
	private static String unmatchedSymbolAtEOF(char symbolExpected) {
		return "ERROR: Unmatched symbol at the end of file. Expected "
				+ symbolExpected + ".";
	}

	/**
	 * Returns an error message for a file that ends with an open /* comment.
	 */
	private static String unfinishedComment() {
		return "ERROR: File ended before closing comment.";
	}

	/**
	 * Returns a message for a file in which all symbols match.
	 */
	private static String allSymbolsMatch() {
		return "No errors found. All symbols match.";
	}
	
	/**
	 * Returns whatever is left in a stack. Very useful for debugging
	 * @return Stack in form of a string
	 */
	public static String getStack(){
		String s = "";
		for(int i = 0; i < stack.size(); i++)
		{
			s = s + stack.pop();
		}
		return s;
	}
}

