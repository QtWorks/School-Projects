package lightsOut;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;

import lightsOut.LOBoard;
import lightsOut.LightsOut;

/**
 * Coordinates between a LOGui (the user interface component)
 * and a LOBoard (which represents the state of the game).
 * @author Tanner Barlow
 *
 */
public class LOController implements ActionListener {
	
	private LightsOut gui;      // The user interface component
	private LOBoard board;  // The state of the game
	private int squareNumber;
	private int row;
	private int col;
	private int enterExit = 0;
	private boolean isBlack;
	private boolean manualSetup;

	
	/**
	 * Creates a controller whose job is to coordinate the behaviors
	 * of gui and board.
	 */
	public LOController (LightsOut gui, LOBoard board) {
		this.gui = gui;
		this.board = board;
	}
	
	/**
	 * What to do when button is clicked
	 */
	public void actionPerformed (ActionEvent e) {

		// Get the button that was clicked
		JButton button = (JButton)e.getSource();		
		
		//Changes name of JButton
		if (button.getText().equals("Exit Manual Setup") || button.getText().equals("Enter Manual Setup"))
		{
			enterExit++;
			if (enterExit % 2 == 0)
			{
				LightsOut.getJButton(0).setText("Enter Manual Setup");
				manualSetup = false; //Exit manual setup mode
			}
			else
			{
				LightsOut.getJButton(0).setText("Exit Manual Setup");
				manualSetup = true;	//Enter manual setup mode		
			}
		}

		// It was one of the 25 board buttons
		else {
			
			if (button.getText().equals("Start New Game")) 
			{ 
				reset();	//Randomizes the board and starts a new game		
			}
			else {
				
			// Get the row and column that were clicked
			row = button.getName().charAt(0) - '0';
			col = button.getName().charAt(1) - '0';
			squareNumber = (row*5+col+1);
			if(manualSetup)
			{
				flipIndividualSwitch(button);
			}
			else
			{
				flipSwitches(button);
			}
			// Make the move.  Illegal moves will result in
			// an exception and are ignored.
			
			try {
				board.move(squareNumber);			
			}
			catch (IllegalArgumentException ex) {
				return;
			}


		}
		}
		}
	//Flips one switch. Used in manual setup mode, but also used as a helper
	//to flip neighboring switches
	public void flipIndividualSwitch(JButton button)
	{
		if (button.getBackground() == Color.BLACK)
		{
			button.setBackground(Color.red);
		}
		else{
			if (button.getBackground() == Color.RED)
			{
				button.setBackground(Color.BLACK);
			}
		}
	}

	//Flip surrounding squares. Accounting for boundary cases (beginnings or ends of columns and rows)
	public void flipSwitches(JButton button) {
		flipIndividualSwitch(button);
		if(row != 0)
		{
			flipIndividualSwitch(BoardPanel.getJButton((squareNumber-1) - 5));
			
		}
		if(row != 4)
		{
			flipIndividualSwitch(BoardPanel.getJButton((squareNumber-1) + 5));
		}
		if(col != 0)
		{
			flipIndividualSwitch(BoardPanel.getJButton((squareNumber-1) - 1));
		}
		if(col != 4)
		{
			flipIndividualSwitch(BoardPanel.getJButton((squareNumber-1) + 1));
		}
		
		
	}
	/**
	 * Resets the controller to begin a new game.
	 */
	public void reset () {
		for (int i = 0; i < 25; i++)
		{
			BoardPanel.getJButton(i).setBackground(BoardPanel.colorRandomizer());
		}
		LightsOut.getJButton(0).setText("Enter Manual Setup");
		manualSetup = false;
	
	}


	public int getSquareNumber()
	{
		return squareNumber;
	}


	public Boolean getManualSetup ()
	{
		return manualSetup;
	}

	
	

}


	


	

	
	
	
	
	
	


	
