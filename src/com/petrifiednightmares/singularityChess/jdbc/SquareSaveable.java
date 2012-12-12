package com.petrifiednightmares.singularityChess.jdbc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.petrifiednightmares.singularityChess.logic.Square;

public class SquareSaveable implements Saveable
{
	// This class is purely used to denote where the pieces are stored. All
	// squares are still initialized as normal even when resuming, so we only
	// need to know the rank and file of this square.
	private char file; // rooks are on file a and h
	private int rank; // white on 1 black on 8

	// Empty constructor for reading
	public SquareSaveable()
	{

	}

	// full constructor for writing
	public SquareSaveable(Square square)
	{
		file = square.getFile();
		rank = square.getRank();
	}

	// ****************************Saveable ****************************/

	public void deserialize(InputStream in) throws IOException
	{
		DataInputStream dataIn = new DataInputStream(in);

		file = dataIn.readChar();
		rank = dataIn.readInt();
	}
	
	public String getIndex()
	{
		return file+" "+rank;
	}

	public void serialize(OutputStream out) throws IOException
	{
		DataOutputStream dataOut = new DataOutputStream(out);

		dataOut.writeChar(file);
		dataOut.writeInt(rank);
	}
}
