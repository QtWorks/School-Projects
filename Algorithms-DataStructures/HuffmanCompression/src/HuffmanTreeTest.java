package assignment12;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HuffmanTreeTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testLargeFile() {
		testCompression("sawyer.txt");
	}
	
	@Test
	public void testSmallFile() {
		testCompression("Mushroom_Publishing.txt");
	}
	
	@Test
	public void testMediumFile() {
		testCompression("original.txt");
	}
	
	@Test
	public void testMazeFile() {
		testCompression("bigMaze.txt");
	}
	
	@Test
	public void testAllX() {
		testCompression("allX.txt");
	}
	
	@Test
	public void testABC() {
		testCompression("abc.txt");
	}
	
	@Test
	public void testBigABC() {
		testCompression("bigABC.txt");
	}
	
	@Test
	public void testDoubleSawyer() {
		testCompression("doubleSawyer.txt");
	}
	
	@Test
	public void testQuadrupleSawyer() {
		testCompression("quadrupleSawyer.txt");
	}
	
	public static void testCompression(String filename){
		System.out.println(filename);
		String original = fileToString(filename);
		//Get name of file without ".txt"
		String name = filename.substring(0, filename.length() - 4);
		//Write compressed file to name + Compression.txt
		CompressionDemo.compressFile(filename, name + "Compression.txt");
		//Write the compressed file to a string
		String compressed = fileToString(name + "Compression.txt");
		//Write decompressed file to name + Decompression.txt
		CompressionDemo.decompressFile(name + "Compression.txt", name + "Decompression.txt");
		//Write decompressed file to a string
		String result = fileToString(name + "Decompression.txt");
		//Print out stats of compression **
		//**Statistic for number of unique characters is printed from **
		//**CompressionDemo.compressFile since the HuffmanTree is **
		//**created there**
		//Size of files
		System.out.println("Original: " + original.length() + " Compressed: " + compressed.length());
		//Difference in sizes
		System.out.println("Difference of: " + (original.length()-compressed.length()));
		//Percentage of compressed / original
		double percent = (((double) compressed.length() / (double) original.length()) * 100);
		DecimalFormat df = new DecimalFormat("#.##");
		System.out.println("File compressed to " + 
		df.format(percent) + "% of its original size\n");
		//Assert that the original is the same as the decompressed file
		assertTrue(original.equals(result));
	}

	public static String fileToString(String filename) {
		String results = "";
		try {
			BufferedReader input = new BufferedReader(new FileReader(filename));
			while (input.ready()) {
				results += (input.readLine());
				results += "\n";
			}
			input.close();
		} catch (Exception e) {
			return results;
		}
		return results;
	}	
}
