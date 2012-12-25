package com.petrifiednightmares.singularityChess.logic.player;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.R;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;
import com.petrifiednightmares.singularityChess.ui.GameUI;

/**
 * This is the player class that is for online games with remote player. The
 * scores are tracked for this mode.
 * 
 * @author formatjam
 * 
 */
public class RemotePlayer extends Player
{

	public RemotePlayer(boolean isWhite, String name, GameDrawingPanel gdp, Game g, GameUI gui)
	{
		super(isWhite, name, gdp, g, gui);
	}

	public void winGame()
	{
		_gdp.showFinishPrompt(R.string.win_title, R.string.win_message);
	}

	public void loseGame()
	{
		_gdp.showFinishPrompt(R.string.lose_title, R.string.lose_message);
	}

	public void tieGame()
	{
		_gdp.showFinishPrompt(R.string.stalemate_title, R.string.stalemate_message);
	}

	/**
	 * this will trigger the Internet communication
	 */
	public void doTurn()
	{

	}

	@Override
	public void promote(AbstractPiece p)
	{
		// TODO Auto-generated method stub

	}

}
