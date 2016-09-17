package assignment9;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * 
 * @authors Joshua Callahan & Tanner Barlow
 *
 */
public class PathFinder {

	// A 2D matrix of nodes on a graph representing squares in a maze grid.
	private static Node[][] maze;
	// The Start location is stored with 2 coordinates in an array.
	private static int[] startingPoint;
	// The output String
	private static String outputString;

	/**
	 * This method will read in a file of a specified String that contains a
	 * maze with a Start location (S) and Destination location (G). The
	 * solveMaze method will return a file containing a similar String with a
	 * dotted path between the two. This path will be the shortest path found
	 * from a breadth-first search.
	 * 
	 * @param inputFile
	 *            with empty maze
	 * @param outputFile
	 *            with solution
	 */
	public static void solveMaze(String inputFile, String outputFile) {

		int height = 0, width = 0;
		try {
			// The String is read from the given input file using a
			// BufferedReader.
			BufferedReader input = new BufferedReader(new FileReader(inputFile));
			// The first line of the given file contains the dimensions of the
			// maze.
			String[] dimensions = input.readLine().split(" ");
			height = Integer.parseInt(dimensions[0]);
			width = Integer.parseInt(dimensions[1]);
			// A 2D array is created to hold nodes at each square.
			maze = new Node[height][width];
			// The maze array is filled with data.
			fillMaze(maze, input, height, width);
		} catch (FileNotFoundException e) {
			System.out.println("The input file was not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("The input file is invalid.");
			e.printStackTrace();
		}

		// A queue is created to contain the nodes to be searched.
		LinkedList<Node> q = new LinkedList<Node>();

		// A BFS is conducted to determine if a path can be found.
		breadthFirstSearch(q, maze);

		// The solution graph is converted back to a file containing a readable
		// String.
		mazeToFile(maze, height, width, outputFile);
	}

	/**
	 * The maze graph is created and filled with the information given from a
	 * BufferedReader.
	 * 
	 * @param maze
	 *            - Graph to be filled with information
	 * @param input
	 *            - BufferedReader information from file
	 * @param height
	 *            - height dimension of maze
	 * @param width
	 *            - width dimension of maze
	 */
	private static void fillMaze(Node[][] maze, BufferedReader input,
			int height, int width) {
		// The data for each Node is filled with a char value.
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				try {
					char c = (char) input.read();
					Node n = new Node();
					// New line characters are ignored.
					if (c != '\n') {
						n.data = c;
					} else {
						n.data = (char) input.read();
					}
					// The node is placed in the 2D array representing a graph.
					maze[i][j] = n;
					if (n.data == 'S') {
						startingPoint = new int[2];
						startingPoint[0] = i;
						startingPoint[1] = j;
					}

				} catch (IOException e) {
					System.out.print("The input file is invalid.");
					e.printStackTrace();
				}
			}
		}

		// The edges of the Nodes are connected to form a graph.
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// If the grid contains a space it is connected to adjacent
				// spaces.
				if (maze[i][j].data != 'X') {
					// Connect Nodes to their top edges.
					if (i > 1 && maze[i - 1][j].data != 'X') {
						maze[i][j].top = maze[i - 1][j];
					}
					// Connect Nodes to their bottom edges.
					if (i < height - 1 && maze[i + 1][j].data != 'X') {
						maze[i][j].bottom = maze[i + 1][j];
					}
					// Connect Nodes to their left edges.
					if (j > 1 && maze[i][j - 1].data != 'X') {
						maze[i][j].left = maze[i][j - 1];
					}
					// Connect Nodes to their right edges.
					if (j < width - 1 && maze[i][j + 1].data != 'X') {
						maze[i][j].right = maze[i][j + 1];
					}
				}
			}
		}
	}

	/**
	 * A breadth first search is conducted over the maze graph starting with the
	 * 'S' square. If the 'G' square can be found without traversing over any
	 * walls then the shortest path will be marked with dots (.).
	 * 
	 * @param q
	 *            - Queue for containing node paths to be searched.
	 * @param maze
	 *            - Graph created from maze grid
	 */
	private static void breadthFirstSearch(LinkedList<Node> q, Node[][] maze) {
		// The queue is given a starting Node that is marked "visited".
		q.add(maze[startingPoint[0]][startingPoint[1]]);
		maze[startingPoint[0]][startingPoint[1]].visited = true;

		while (!q.isEmpty()) {
			Node temp = q.remove();
			temp.visited = true;
			// If the goal is found, retrace steps and place a period in each
			// square back to the Start Node.
			if (temp.data == 'G') {
				temp = temp.prev;
				while (temp.prev != null) {
					temp.data = '.';
					temp = temp.prev;
				}
			}
			// Search a layer further into Node connected by the top edge.
			if (temp.top != null && !temp.top.visited) {
				q.add(temp.top);
				temp.top.visited = true;
				temp.top.prev = temp;
			}
			// Search a layer further into Node connected by the bottom edge.
			if (temp.bottom != null && !temp.bottom.visited) {
				q.add(temp.bottom);
				temp.bottom.visited = true;
				temp.bottom.prev = temp;
			}
			// Search a layer further into Node connected by the left edge.
			if (temp.left != null && !temp.left.visited) {
				q.add(temp.left);
				temp.left.visited = true;
				temp.left.prev = temp;
			}
			// Search a layer further into Node connected by the right edge.
			if (temp.right != null && !temp.right.visited) {
				q.add(temp.right);
				temp.right.visited = true;
				temp.right.prev = temp;
			}
		}
	}

	/**
	 * This method will write a 2D maze solution array representation into a
	 * file containing a square String grid.
	 * 
	 * @param maze
	 *            - Solution graph
	 * @param height
	 *            - Dimension
	 * @param width
	 *            - Dimension
	 * @param outputFile
	 *            - Filename to be written
	 */
	private static void mazeToFile(Node[][] maze, int height, int width,
			String outputFile) {
		try {
			PrintWriter output = new PrintWriter(new FileWriter(outputFile));
			String s = height + " " + width + '\n';
			// A String block is created from the data contained in the Nodes.
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					s += maze[i][j].data;
				}
				s += '\n';
			}
			output.println(s);
			outputString = s;
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The Output String written to a file is allowed to be accessible for
	 * testing purposes.
	 * 
	 * @return output String written to a file.
	 */
	public static String getOutputString() {
		return outputString;
	}

	/**
	 * Nodes are created to be linked in a 2D grid graph. They contain a char
	 * data value. They also contain pointers to 5 other Nodes.
	 */
	static class Node {
		char data;
		boolean visited;
		Node prev, top, bottom, left, right;
	}

}
