package com.petrifiednightmares.singularityChess.logic.player;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;

/**
 * This is the player class that is for human vs human offline games. The scores
 * are not tracked for this mode.
 * 
 * @author jason
 * 
 */
public class NonTrackedPlayer extends Player
{

	public NonTrackedPlayer(boolean isWhite, String name, GameDrawingPanel gdp)
	{
		super(isWhite, name, gdp);
	}

	public void winGame()
	{
		_gdp.showFinishPrompt("Checkmate!", _name + " won!");
	}

	public void loseGame()
	{
		// Do nothing, only have the win show something
	}

	public void tieGame()
	{
		_gdp.showFinishPrompt("Stalemate!", "This is stale meat. Should have put it in the fridge.");
	}

	/**
	 * Since this is a human player, this doesn't do anything
	 */
	public void doTurn()
	{

	}

}
