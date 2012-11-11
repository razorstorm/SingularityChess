package com.petrifiednightmares.singularityChess.pieces;

import java.util.Set;

import android.graphics.Canvas;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.GameException;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.logic.Square;

public abstract class AbstractPiece
{
	protected Game game;
	protected Square location;
	protected boolean alive;
	protected boolean isWhite;
	private String icon; //DO NOT CHANGE THIS, let's keep the unicode as a label
	//If using bitmaps again, add a new variable
	private boolean _isSelected;

	public AbstractPiece(Game game, Square location, boolean isWhite, String icon)
	{
		this.game = game;
		this.location = location;
		this.alive = true;
		this.isWhite = isWhite;
		if(icon == null)
		{
			System.err.println("Piece: "+this+" got null");
		}
		this.icon = icon;
	}

	public void select()
	{
		_isSelected = true;
	}

	public void unselect()
	{
		_isSelected = false;
	}

	public boolean getIsSelected()
	{
		return _isSelected;
	}

	public abstract Set<Square> getMoves() throws GameException;

	public void onDraw(Canvas c, int x, int y)
	{
		float textWidth = GameDrawingPanel.piecePaint.measureText(getIcon());

		c.drawText(getIcon(), x - textWidth / 2, y,
				GameDrawingPanel.piecePaint);
	}

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

	public String getIcon()
	{
		return icon;
	}


}
