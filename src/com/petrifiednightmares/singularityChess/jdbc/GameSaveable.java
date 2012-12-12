package com.petrifiednightmares.singularityChess.jdbc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.petrifiednightmares.singularityChess.logging.MoveLogger;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;

public class GameSaveable implements Saveable
{

	boolean isWhiteTurn;
	AbstractPiece[] whitePieces;
	AbstractPiece[] blackPieces;
	
	PieceSaveable[] whitePieceSaveables;
	PieceSaveable[] blackPieceSaveables;
	
	MoveLoggerSaveable mls;
	
	
	public boolean isWhiteTurn()
	{
		return isWhiteTurn;
	}

	public AbstractPiece[] getWhitePieces()
	{
		return whitePieces;
	}

	public AbstractPiece[] getBlackPieces()
	{
		return blackPieces;
	}


	// Empty constructor for reading
	public GameSaveable()
	{

	}

	// full constructor for writing
	public GameSaveable(boolean isWhiteTurn, AbstractPiece[] whitePieces,
			AbstractPiece[] blackPieces, MoveLogger ml)
	{
		this.isWhiteTurn = isWhiteTurn;
		
		whitePieceSaveables = new PieceSaveable[16];
		blackPieceSaveables = new PieceSaveable[16];
		
		for(int i = 0; i < 16; i++)
		{
			whitePieceSaveables[i] = new PieceSaveable(whitePieces[i]);
			blackPieceSaveables[i] = new PieceSaveable(blackPieces[i]);
		}
		
		mls = new MoveLoggerSaveable(ml);
	}

	// ****************************Saveable ****************************/

	//deserialize by calling getPiece() to build AbstractPiece[] 
	public void deserialize(InputStream in) throws IOException
	{
	

	}

	public void serialize(OutputStream out) throws IOException
	{
		DataOutputStream dataOut = new DataOutputStream(out);

		dataOut.writeBoolean(isWhiteTurn);
		
		for(int i = 0; i < 16; i++)
		{
			whitePieceSaveables[i].serialize(dataOut);
		}
		
		for(int i = 0; i < 16; i++)
		{
			blackPieceSaveables[i].serialize(dataOut);
		}
		
		mls.serialize(out);
	}
}
