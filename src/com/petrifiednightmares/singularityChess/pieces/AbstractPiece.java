package com.petrifiednightmares.singularityChess.pieces;

import java.util.Set;

import android.graphics.Bitmap;

import com.petrifiednightmares.singularityChess.GameException;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.logic.Square;


public abstract class AbstractPiece
{
	protected Game game;
	protected Square location;
	protected boolean alive;
	protected boolean isWhite;
	protected Bitmap icon;
	private boolean isSelected;

	public AbstractPiece(Game game, Square location, boolean isWhite, Bitmap icon)
	{
		this.game = game;
		this.location = location;
		this.alive = true;
		this.isWhite = isWhite;
		this.icon = icon;
	}
	
	public void select()
	{
		isSelected=true;
	}
	public void unselect()
	{
		isSelected=false;
	}
	
	public boolean getIsSelected()
	{
		return isSelected;
	}

	public abstract Set<Square> getMoves() throws GameException;

	// returns captured pieces
	public AbstractPiece makeMove(Square target)
	{
		this.location.removePiece();
		this.location = target;
		AbstractPiece captured = this.location.getPiece();
		this.location.addPiece(this);
		if (captured != null)
		{
			captured.getCaptured();
		}
		return captured;
	}

	public void getCaptured()
	{
		this.alive = false;
	}

	public Game getGame()
	{
		return game;
	}

	public Square getLocation()
	{
		return location;
	}

	public boolean isAlive()
	{
		return alive;
	}

	public boolean isWhite()
	{
		return isWhite;
	}

	public boolean isCapturable()
	{
		return true;
	}
}
