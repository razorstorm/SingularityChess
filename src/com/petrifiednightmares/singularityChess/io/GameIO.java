package com.petrifiednightmares.singularityChess.io;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.content.Context;

public class GameIO
{

	private static enum StorageOption
	{
		STDOUT, SQLITE, FILE, NETWORK
	};

	private static StorageOption storageOption = StorageOption.FILE;
	
	//determine where the shiet is saved
	private static String fileName = "game_state";
	
	public static void intentionSaveGame()
	{
		 fileName = "game_state";
	}

	// for saving
	public static OutputStream getOutputStream(Context c) throws FileNotFoundException
	{
		switch (storageOption)
		{
		case STDOUT:
			return new BufferedOutputStream(System.out);
		case FILE:
			FileOutputStream fos = c.openFileOutput(fileName, Context.MODE_PRIVATE);
			return new BufferedOutputStream(fos);
		case SQLITE:
			return null;
		default:
			return null;
		}
	}

	// for reading
	// public static InputStream getInputStream()
	// {
	// // return new BufferedInputStream(new FileInputStream(file));
	//
	// }
}
