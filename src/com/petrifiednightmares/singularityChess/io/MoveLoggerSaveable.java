package com.petrifiednightmares.singularityChess.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

import com.petrifiednightmares.singularityChess.logging.Action;
import com.petrifiednightmares.singularityChess.logging.MoveLogger;

public class MoveLoggerSaveable implements Saveable
{

	ActionSaveable[] actionSaveables;
	String[] capturedWhitePieces, capturedBlackPieces;

	int numActions;

	// For resuming
	private MoveLogger _ml;

	// Empty constructor for reading
	public MoveLoggerSaveable()
	{

	}

	public MoveLogger getMoveLogger()
	{
		return _ml;
	}

	// full constructor for writing
	public MoveLoggerSaveable(MoveLogger ml)
	{
		numActions = ml.getNumMoves();
		actionSaveables = new ActionSaveable[numActions];
		int i = 0;
		for (Action a : ml.getMoves())
		{
			actionSaveables[i++] = new ActionSaveable(a);
		}

		LinkedList<String> whitePieces = ml.getCapturedWhitePieces();
		LinkedList<String> blackPieces = ml.getCapturedBlackPieces();

		capturedWhitePieces = new String[whitePieces.size()];
		capturedBlackPieces = new String[blackPieces.size()];

		i = 0;
		for (String p : whitePieces)
		{
			capturedWhitePieces[i++] = p;
		}
		i = 0;
		for (String p : blackPieces)
		{
			capturedBlackPieces[i++] = p;
		}
	}

	public void deserialize(InputStream in) throws IOException
	{
		DataInputStream dataIn = new DataInputStream(in);

		int length = dataIn.readInt();

		LinkedList<Action> actions = new LinkedList<Action>();

		ActionSaveable as;
		for (int i = 0; i < length; i++)
		{
			as = new ActionSaveable();
			as.deserialize(dataIn);

			actions.add(as.getAction());
		}

		LinkedList<String> whitePieces = new LinkedList<String>();
		LinkedList<String> blackPieces = new LinkedList<String>();

		length = dataIn.readInt();

		for (int i = 0; i < length; i++)
		{
			int strLength = dataIn.readInt(); 
			char[] charArray = new char[strLength];

			for (int j = 0; j < strLength; j++)
				charArray[j] = dataIn.readChar();

			whitePieces.add(new String(charArray));
		}
		
		length = dataIn.readInt();

		for (int i = 0; i < length; i++)
		{
			int strLength = dataIn.readInt(); 
			char[] charArray = new char[strLength];

			for (int j = 0; j < strLength; j++)
				charArray[j] = dataIn.readChar();

			blackPieces.add(new String(charArray));
		}

		_ml = new MoveLogger(actions, whitePieces, blackPieces);
	}

	public void serialize(OutputStream out) throws IOException
	{
		DataOutputStream dataOut = new DataOutputStream(out);

		dataOut.writeInt(numActions);
		for (ActionSaveable a : actionSaveables)
		{
			a.serialize(out);
		}

		dataOut.writeInt(capturedWhitePieces.length);

		for (String p : capturedWhitePieces)
		{
			dataOut.writeInt(p.length());
			dataOut.writeChars(p);
		}

		dataOut.writeInt(capturedBlackPieces.length);

		for (String p : capturedBlackPieces)
		{
			dataOut.writeInt(p.length());
			dataOut.writeChars(p);
		}
	}
}
