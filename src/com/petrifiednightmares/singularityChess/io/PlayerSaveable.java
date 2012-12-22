package com.petrifiednightmares.singularityChess.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.logic.player.AIPlayer;
import com.petrifiednightmares.singularityChess.logic.player.NonTrackedPlayer;
import com.petrifiednightmares.singularityChess.logic.player.Player;
import com.petrifiednightmares.singularityChess.logic.player.TrackedPlayer;
import com.petrifiednightmares.singularityChess.ui.GameUI;

public class PlayerSaveable implements Saveable
{
	String				playerType;
	String				name;
	boolean				isWhite;
	Game				g;
	GameDrawingPanel	gdp;
	GameUI				gui;

	// for resuming
	Player				p;

	public Player getPlayer()
	{
		return p;
	}

	// Empty constructor for reading
	public PlayerSaveable(Game g, GameDrawingPanel gdp, GameUI gui)
	{
		this.g = g;
		this.gdp = gdp;
		this.gui = gui;
	}

	// full constructor for writing
	public PlayerSaveable(Player p)
	{
		this.playerType = p.getClass().getSimpleName();
		this.name = p.getName();
		this.isWhite = p.isWhite();

	}

	// ****************************Saveable ****************************/

	// NOTE: This method has a side effect. it will modify _game's squares to
	// add the pieces.
	public void deserialize(InputStream in) throws IOException
	{
		DataInputStream dataIn = new DataInputStream(in);

		int length = dataIn.readInt();

		char[] charArray = new char[length];// a char array to hold the name
											// chars

		for (int i = 0; i < length; i++)
			charArray[i] = dataIn.readChar(); // convert to string and allocate
												// to name

		String playerType = new String(charArray);

		length = dataIn.readInt();

		charArray = new char[length];// a char array to hold the name
										// chars

		for (int i = 0; i < length; i++)
			charArray[i] = dataIn.readChar(); // convert to string and allocate
												// to name

		String name = new String(charArray);

		boolean isWhite = dataIn.readBoolean();

		if (playerType.equals("AIPlayer"))
		{
			p = new AIPlayer(isWhite, name, gdp, g, gui);
		}
		else if (playerType.equals("NonTrackedPlayer"))
		{
			p = new NonTrackedPlayer(isWhite, name, gdp, g, gui);
		}
		else if (playerType.equals("TrackedPlayer"))
		{
			p = new TrackedPlayer(isWhite, name, gdp, g, gui);
		}
	}

	public void serialize(OutputStream out) throws IOException
	{
		DataOutputStream dataOut = new DataOutputStream(out);

		dataOut.writeInt(playerType.length());
		dataOut.writeChars(playerType);

		dataOut.writeInt(name.length());
		dataOut.writeChars(name);

		dataOut.writeBoolean(isWhite);
	}
}
