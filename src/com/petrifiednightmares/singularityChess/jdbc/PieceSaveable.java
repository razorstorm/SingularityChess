package com.petrifiednightmares.singularityChess.jdbc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.logic.Square;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;
import com.petrifiednightmares.singularityChess.pieces.King;

public class PieceSaveable implements Saveable
{

	boolean isWhite, isAlive;

	AbstractPiece.PieceType pieceType;

	SquareSaveable location;

	// for resuming
	AbstractPiece p;
	Game _game;

	public AbstractPiece getPiece()
	{
		return p;
	}

	// Empty constructor for reading
	public PieceSaveable(Game g)
	{
		this._game = g;
	}

	// full constructor for writing
	public PieceSaveable(AbstractPiece p)
	{
		isWhite = p.isWhite();
		isAlive = p.isAlive();

		this.location = new SquareSaveable(p.getLocation());
		this.pieceType = p.getType();
	}

	// ****************************Saveable ****************************/

	public void deserialize(InputStream in) throws IOException
	{
		DataInputStream dataIn = new DataInputStream(in);

		int type = dataIn.readInt();
		boolean isWhite = dataIn.readBoolean();
		boolean isAlive = dataIn.readBoolean();
		boolean hasLocation = dataIn.readBoolean();

		SquareSaveable locationSaveable;
		Square location;
		if (hasLocation)
		{
			locationSaveable = new SquareSaveable();
			locationSaveable.deserialize(in);
			location = _game.getBoard().getSquares().get(locationSaveable.getIndex());
		} else
		{
			location = null;
		}

		switch (type)
		{
		case AbstractPiece.PieceType.King.getValue():
			p = new King(_game, location, isWhite);
			p.setIsAlive(isAlive);
			break;
			
		case AbstractPiece.PieceType.Queen.getValue():
			break;
		}

	}

	public void serialize(OutputStream out) throws IOException
	{
		DataOutputStream dataOut = new DataOutputStream(out);

		dataOut.writeInt((int) pieceType.getValue());
		dataOut.writeBoolean(isWhite);
		dataOut.writeBoolean(isAlive);

		if (location != null)
		{
			dataOut.writeBoolean(true);
			location.serialize(dataOut);
		} else
		{
			dataOut.writeBoolean(false);
		}
	}
}
