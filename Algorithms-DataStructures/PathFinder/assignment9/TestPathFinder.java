package assignment9;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class TestPathFinder {

	@Test
	public void testSolveBigMaze() {
		assertTrue(solveMazeCorrect("bigMaze.txt", "bigMazeSol.txt"));
	}
	
	@Test
	public void testSolveClassic() {
		assertTrue(solveMazeCorrect("classic.txt", "classicSol.txt"));
	}

	@Test
	public void testSolveDemo() {
		assertTrue(solveMazeCorrect("demoMaze.txt", "demoMazeSol.txt"));
	}
	
	@Test
	public void testSolveMedium() {
		assertTrue(solveMazeCorrect("mediumMaze.txt", "mediumMazeSol.txt"));
	}
	
	@Test
	public void testSolveRandomMaze() {
		assertTrue(solveMazeCorrect("randomMaze.txt", "randomMazeSol.txt"));
	}
	
	@Test
	public void testSolveStraight() {
		assertTrue(solveMazeCorrect("straight.txt", "straightSol.txt"));
	}
	
	@Test
	public void testSolveTinyMaze() {
		assertTrue(solveMazeCorrect("tinyMaze.txt", "tinyMazeSol.txt"));
	}
	
	@Test
	public void testSolveTinyOpen() {
		assertTrue(solveMazeCorrect("tinyOpen.txt", "tinyOpenSol.txt"));
	}
	
	@Test
	public void testSolveTurn() {
		assertTrue(solveMazeCorrect("turn.txt", "turnSol.txt"));
	}
	
	@Test
	public void testSolveUnsolvable() {
		assertTrue(solveMazeCorrect("unsolvable.txt", "unsolvableSol.txt"));
	}
	
	@Test
	public void testSolveSimple() {
		assertTrue(solveMazeCorrect("classic2.txt", "classic2Sol.txt"));
	}
	
	@Test
	public void testSolveStraightHuge() {
		assertTrue(solveMazeCorrect("straightHuge.txt", "straightHugeSol.txt"));
	}
	
	@Test
	public void testSolveHuge() {
		assertTrue(solveMazeCorrect("huge.txt", "hugeSol.txt"));
	}

	@Test
	public void testSolveStraightHugest() {
		assertTrue(solveMazeCorrect("hugeOpen.txt", "hugeOpenSol.txt"));
	}

	private static boolean solveMazeCorrect(String pathnameMaze, String pathnameSolution){
		//Output name is the name of the pathname minus the last 4 characters ".txt"
		PathFinder.solveMaze(pathnameMaze, pathnameMaze.substring(0,pathnameMaze.length() - 4)+ "Output.txt");
		String solution = fileToString(pathnameSolution);
		String output = PathFinder.getOutputString();
		if(output.equals(solution)){
			return true;
		}else{
			//Test to see if there are the same number of dots
			//Means that the same number of steps were taken to get from the start
			//to the finish, and is just another shortest path to take
			int dotCountMaze = 0, dotCountSol = 0;
			for(int i = 0; i < solution.length(); i++){
				if(output.charAt(i) == '.'){
					dotCountMaze++;
				}
				if(solution.charAt(i) == '.'){
					dotCountSol++;
				}
			}
			if(dotCountMaze == dotCountSol){
//				System.out.println(pathnameMaze + ": " + dotCountMaze);
				return true;
			}else{
				System.err.println(pathnameMaze + ": " + dotCountMaze);
				System.err.println(pathnameSolution + ": " + dotCountSol);
				return false;
			}
		}
	}

	private static String fileToString(String filename) {
		String s = "";
		try {
			BufferedReader input = new BufferedReader(new FileReader(filename));
			while(input.ready()){
				s += (char) input.read();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
}
