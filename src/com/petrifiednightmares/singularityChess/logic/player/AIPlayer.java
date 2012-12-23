package com.petrifiednightmares.singularityChess.logic.player;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.InvalidMoveException;
import com.petrifiednightmares.singularityChess.ai.AIEngine;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;
import com.petrifiednightmares.singularityChess.ui.GameUI;

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

	public AIPlayer(boolean isWhite, String name, GameDrawingPanel gdp, Game g, GameUI gui)
	{
		super(isWhite, name, gdp, g, gui);
		this._game = g;
	}

	/**
	 * Machine player has no emotions, don't show it shit.
	 */
	public void winGame()
	{		
		_gdp.showFinishPrompt("You lose!", "My lovely AI Lila beat you! :P");
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
		// 1. start AI engine, pass in the current state of game
		AIEngine aiEngine = new AIEngine(this._game);
		// 2. calculate for the next move, result: selecting piece, and target
		// square.
		aiEngine.calcNextMove();
		// 3. use Game.java interface to select the piece and make move.
		
		this._game.select(aiEngine.getSelectingPiece());
		try
		{
			this._game.makeMove(aiEngine.getTarget());
		}
		catch (InvalidMoveException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void promote(AbstractPiece p)
	{
		_game.select(p);
		_game.promotePiece(AbstractPiece.PieceType.Queen);
	}
}
