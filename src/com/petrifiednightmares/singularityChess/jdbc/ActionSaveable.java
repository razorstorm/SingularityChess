package com.petrifiednightmares.singularityChess.jdbc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.petrifiednightmares.singularityChess.logging.Action;

public class ActionSaveable implements Saveable
{
	private String simplifiedString; // for sake of laze, when saving and resuming, actions lose their dynamic data lololol
	
	//For resuming game
	Action a;

	// Empty constructor for reading
	public ActionSaveable()
	{

	}

	// full constructor for writing
	public ActionSaveable(Action a)
	{
		simplifiedString = a.toString();
	}

	public void deserialize(InputStream in) throws IOException
	{
		DataInputStream dataIn = new DataInputStream(in);

		int length = dataIn.readInt();

		char[] charArray = new char[length];// a char array to hold the name
											// chars

		for (int i = 0; i < length; i++)
			charArray[i] = dataIn.readChar(); // convert to string and allocate
												// to name

		String action = new String(charArray);
		a = new Action(action);
	}
	
	//For resuming the game
	public Action getAction()
	{
		return a;
	}

	public void serialize(OutputStream out) throws IOException
	{
		DataOutputStream dataOut = new DataOutputStream(out);

		dataOut.writeInt(simplifiedString.length());
		
		dataOut.writeChars(simplifiedString);

	}
}
