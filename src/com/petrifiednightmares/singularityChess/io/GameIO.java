package com.petrifiednightmares.singularityChess.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.os.Environment;

public class GameIO
{

	public static enum StorageOption
	{
		STDOUT, SQLITE, FILE, NETWORK, IMAGE_CACHE
	};

	public static enum Intention
	{
		SAVE_GAME, CACHE_BG
	}

	private static String getFileName(Intention i)
	{
		switch (i)
		{
			case SAVE_GAME:
				return "game_state";
			case CACHE_BG:
				return "bg_bitmap.png";
		}
		return null;
	}

	public static boolean hasFile(Context c, Intention i, StorageOption storageOption)
	{
		String fileName = getFileName(i);
		File toCheck;
		switch (storageOption)
		{
			case FILE:
				toCheck = new File(c.getExternalFilesDir(null), fileName);
				return toCheck.exists();
			case IMAGE_CACHE:
				toCheck = new File(c.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName);
				return toCheck.exists();
			default:
				return false;
		}
	}

	public static boolean removeFile(Context c, Intention i, StorageOption storageOption)
	{
		String fileName = getFileName(i);
		File toBeDeleted;
		switch (storageOption)
		{
			case FILE:
				toBeDeleted = new File(c.getExternalFilesDir(null), fileName);
				return toBeDeleted.delete();
			case IMAGE_CACHE:
				toBeDeleted = new File(c.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
						fileName);
				return toBeDeleted.delete();
			default:
				return false;
		}
	}

	public static String getCacheBgFileName(Context c)
	{
		File picturesDir = c.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

		if (picturesDir != null)
			return picturesDir.getAbsolutePath() + "/" + getFileName(Intention.CACHE_BG);
		else
			return null;
	}

	// for saving
	public static OutputStream getOutputStream(Context c, Intention i, StorageOption storageOption)
			throws FileNotFoundException
	{
		String fileName = getFileName(i);
		FileOutputStream fos;
		switch (storageOption)
		{
			case STDOUT:
				return new BufferedOutputStream(System.out);
			case FILE:
				fos = new FileOutputStream(new File(c.getExternalFilesDir(null), fileName));
				// FileOutputStream fos = _context.openFileOutput(_fileName,
				// Context.MODE_PRIVATE);
				return new BufferedOutputStream(fos);
			case SQLITE:
				return null;
			case IMAGE_CACHE:
				fos = new FileOutputStream(new File(
						c.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName));
				return new BufferedOutputStream(fos);
			default:
				return null;
		}
	}

	// for reading
	public static InputStream getInputStream(Context c, Intention i, StorageOption storageOption)
			throws FileNotFoundException
	{
		String fileName = getFileName(i);
		switch (storageOption)
		{
			case STDOUT:
				return null;
			case FILE:
				FileInputStream fos = new FileInputStream(new File(c.getExternalFilesDir(null),
						fileName));
				return new BufferedInputStream(fos);
			case IMAGE_CACHE:
				fos = new FileInputStream(new File(
						c.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName));
				return new BufferedInputStream(fos);
			case SQLITE:
				return null;
			default:
				return null;
		}

	}

	public static void closeSilently(Closeable resource)
	{
		try
		{
			if (resource != null)
			{
				resource.close();
			}
		}
		catch (Exception ex)
		{
		}
	}
}
