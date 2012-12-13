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
	int numActions;

	// For resuming
	MoveLogger ml;

	// Empty constructor for reading
	public MoveLoggerSaveable()
	{

	}
	
	public MoveLogger getMoveLogger()
	{
		return ml;
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
	}

	public void serialize(OutputStream out) throws IOException
	{
		DataOutputStream dataOut = new DataOutputStream(out);

		dataOut.writeInt(numActions);
		for (ActionSaveable a : actionSaveables)
		{
			a.serialize(out);
		}

	}
}
