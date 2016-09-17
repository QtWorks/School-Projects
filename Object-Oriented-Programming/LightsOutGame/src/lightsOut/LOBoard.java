package lightsOut;

import java.awt.Color;

import javax.swing.JOptionPane;

import lightsOut.LightsOut;
import lightsOut.LOController;

public class LOBoard {

	//Initializes an array with length 25 that contains a 0 as its default
	private Boolean [] board = new Boolean [25];
	private LightsOut gui;
	private LOController controller;

    /**
     * Constructs an empty board in which X has won zero times, O has won zero
     * times, and there have been no draws.
     */
	
    

    /**
     
     */
    public void move (int square)
    {	//If win, throw IAE
    	if (isWon()) //Throws exception and notifies the player
    	{
    		JOptionPane.showMessageDialog(null, "YOU WIN! \nClick 'Start New Game' if you wish to play again");
    		throw new IllegalArgumentException();    		
    	}
    	//If square is out of bounds, throw IAE
    	if (square < 1 || square > 25)
    	{
    		throw new IllegalArgumentException();
    	}

    	
    }



	/**
     * Reports whether all the lights have been turned out to win the game
     */
    public boolean isWon () //
    {
    	for (int i = 0; i < 25; i++)
    	{	//getColor returns a boolean if square is black
    		if (!(BoardPanel.getColor((BoardPanel.getJButton(i)))))
    		{
    			return false;
    		} 		
    	}
    	return true;

    }


	

}
