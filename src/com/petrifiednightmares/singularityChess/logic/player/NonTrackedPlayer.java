package com.petrifiednightmares.singularityChess.logic.player;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.R;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.ui.GameUI;

/**
 * This is the player class that is for human vs human offline games. The scores
 * are not tracked for this mode.
 * 
 * @author jason
 * 
 */
public class NonTrackedPlayer extends HumanPlayer
{

	public NonTrackedPlayer(boolean isWhite, String name, GameDrawingPanel gdp, Game g, GameUI gui)
	{
		super(isWhite, name, gdp, g, gui);
	}

	public void winGame()
	{
		_gdp.showFinishPrompt(R.string.checkmate_title, (_isWhite ? R.string.white_win_message
				: R.string.black_win_message));
	}
}
