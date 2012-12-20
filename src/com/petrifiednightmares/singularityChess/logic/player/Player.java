package com.petrifiednightmares.singularityChess.logic.player;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;

public abstract class Player
{
	protected boolean	_isWhite;
	protected String	_name;
	GameDrawingPanel	_gdp;

	public Player(boolean isWhite, String name, GameDrawingPanel gdp)
	{
		this._isWhite = isWhite;
		this._name = name;
		this._gdp = gdp;
	}

	/**
	 * These methods are used for any post game activities such as keep track of
	 * score
	 * 
	 * Machine players do nothing in these methods
	 */
	public abstract void winGame();

	public abstract void loseGame();

	public abstract void tieGame();

	// Tell the player that it has to make a move.
	// This doesn't return the move or anything
	// The player will make the move by telling Game direction.
	public abstract void doTurn();

	public boolean isWhite()
	{
		return _isWhite;
	}

	public String getName()
	{
		return _name;
	}
}
