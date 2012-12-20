package com.petrifiednightmares.singularityChess.logic.player;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.logic.Game;
import android.util.Log;

/**
 * This is the player class that is for human vs computer offline games. The
 * scores are tracked for this mode.
 * 
 * @author jason
 * 
 */
public class AIPlayer extends Player
{

	private Game	_game;

	public AIPlayer(boolean isWhite, String name, GameDrawingPanel gdp, Game g)
	{
		super(isWhite, name, gdp);
		this._game = g;
	}

	/**
	 * Machine player has no emotions, don't show it shit.
	 */
	public void winGame()
	{
	}

	/**
	 * Machine player has no emotions, don't show it shit.
	 */
	public void loseGame()
	{
	}

	public void tieGame()
	{
	}

	public void doTurn()
	{
		// TODO DEREK, do shit here.
		//when ready, call _game.makeMove() 'n shit
		Log.i("SChess", "AI Player do turns!");
		//first select a piece.
		_game.finishMove();
	}

}
