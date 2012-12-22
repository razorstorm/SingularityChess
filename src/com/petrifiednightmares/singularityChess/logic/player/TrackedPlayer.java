package com.petrifiednightmares.singularityChess.logic.player;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.ui.GameUI;

/**
 * This is the player class that is for human vs computer offline games. The
 * scores are tracked for this mode.
 * 
 * @author jason
 * 
 */
public class TrackedPlayer extends HumanPlayer
{

	public TrackedPlayer(boolean isWhite, String name, GameDrawingPanel gdp, Game g, GameUI gui)
	{
		super(isWhite, name, gdp, g, gui);
	}

	public void winGame()
	{
		//stat tracking goes here
		super.winGame();
	}

	public void loseGame()
	{
		//stat tracking goes here
		super.loseGame();
	}

	public void tieGame()
	{
		//stat tracking goes here
		super.tieGame();
	}

}
