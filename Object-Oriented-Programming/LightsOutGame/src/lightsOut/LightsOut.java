package lightsOut;

import javax.swing.*;

import java.awt.Color.*;

import lightsOut.BoardPanel;
import lightsOut.LOBoard;
import lightsOut.LOController;

import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
/**
 * Represents a LightsOut game board
 * @author Tanner Barlow
 *
 */
public class LightsOut extends JFrame {

	public static void main (String[] args) {
		LightsOut log = new LightsOut();
		log.setVisible(true);
	}

	private BoardPanel boardPanel;  //The main part of the GUI
	private LOBoard board;
	private LOController controller;
	private static ArrayList <JButton> bArray = new ArrayList<JButton>();	

	public LightsOut () {
		// When this window is closed, the program exits
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Lights Out!");		
		//Represents the state of the game
		board = new LOBoard();
		
		//Controls the interaction between the GUI and the board
		controller = new LOController (this,board);
		
		//Top-level panel within the window
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		// Lay out the main panel
		boardPanel = new BoardPanel(controller);
		mainPanel.add(boardPanel, "Center");
		JPanel labelsPanel = new JPanel();
		JLabel objective = new JLabel ("Your objective is to turn off all of the lights...Good Luck!");
		labelsPanel.setLayout(new GridLayout(1,2));
		labelsPanel.add(objective);
		//Panel for buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1,2));
		//Will switch to Exit Manual Setup when clicked
		JButton enterManualSetup = new JButton("Enter Manual Setup");
		enterManualSetup.addActionListener(controller);
		buttonsPanel.add(enterManualSetup);
		//bArray holds the two buttons to make them accessibl in other classes
		bArray.add(enterManualSetup);
		JButton reset = new JButton("Start New Game");
		reset.addActionListener(controller);
		buttonsPanel.add(reset);	
		bArray.add(reset);		
		//Put labels up top and the buttons on the bottom
		mainPanel.add(buttonsPanel, "South");
		mainPanel.add(labelsPanel,"North");
		
		// Compose the top-level window and get going.
		setContentPane(mainPanel);
		setSize(600,600);	
	}
	
	//public static JButton getJButton (int n)
	public static JButton getJButton (int n)
	{
		return bArray.get(n);
	}


	
}

/**
 * Represents the playing area of a Lights Out GUI.
 */
class BoardPanel extends JPanel {
	private static ArrayList <JButton> jButtonArrayList = new ArrayList<JButton>();	
	
	/**
	 * Creates a BoardPanel given the controller and the board state.
	 */
	public BoardPanel (ActionListener listener) {
		setLayout(new GridLayout(5,5));
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				JButton b = new JButton(" ");
				//Randomizes between red and black
				b.setBackground(colorRandomizer());
				b.setName("" + i + j);
				//Add to an array to make them accessible elsewhere
				jButtonArrayList.add(b);
				add(b);
				b.addActionListener(listener);
			}
		}
	}
	
	public static JButton getJButton (int n)
	{
		return jButtonArrayList.get(n);
	}
	public static boolean getColor(JButton b)
	{
		if (b.getBackground().equals(Color.BLACK))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static Color colorRandomizer()
	{
		Random r = new Random();
		int rand = r.nextInt();
		if (rand % 2 == 0)
		{
			return Color.BLACK;
		}
		else 
		{
			return Color.RED; 
		}
	}
	
	
}
	

	
	
	
	

	
	
	
	
	
	
	
	
	
	

