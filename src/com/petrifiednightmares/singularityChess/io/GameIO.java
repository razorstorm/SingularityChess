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

	// determine where the shiet is saved
	private static String			_fileName	= "game_state";

	private static Context			_context;

	private static StorageOption	_storageOption;

	public static void setContext(Context context)
	{
		_context = context;
	}

	public static void intentionSaveGame()
	{
		_fileName = "game_state";
		_storageOption = StorageOption.FILE;
	}

	public static void intentionCacheBg()
	{
		_fileName = "bg_bitmap.png";
		_storageOption = StorageOption.IMAGE_CACHE;
	}

	public static boolean hasFile()
	{
		File toCheck;
		switch (_storageOption)
		{
			case FILE:
				toCheck = new File(_context.getExternalFilesDir(null), _fileName);
				return toCheck.exists();
			case IMAGE_CACHE:
				toCheck = new File(_context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
						_fileName);
				return toCheck.exists();
			default:
				return false;
		}
	}

	public static boolean removeFile()
	{
		File toBeDeleted;
		switch (_storageOption)
		{
			case FILE:
				toBeDeleted = new File(_context.getExternalFilesDir(null), _fileName);
				return toBeDeleted.delete();
			case IMAGE_CACHE:
				toBeDeleted = new File(
						_context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), _fileName);
				return toBeDeleted.delete();
			default:
				return false;
		}
	}

	public static String getCacheBgFileName()
	{
		return _context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/"
				+ _fileName;
	}

	// for saving
	public static OutputStream getOutputStream() throws FileNotFoundException
	{
		FileOutputStream fos;
		switch (_storageOption)
		{
			case STDOUT:
				return new BufferedOutputStream(System.out);
			case FILE:
				fos = new FileOutputStream(new File(_context.getExternalFilesDir(null), _fileName));
				// FileOutputStream fos = _context.openFileOutput(_fileName,
				// Context.MODE_PRIVATE);
				return new BufferedOutputStream(fos);
			case SQLITE:
				return null;
			case IMAGE_CACHE:
				fos = new FileOutputStream(new File(
						_context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), _fileName));
				return new BufferedOutputStream(fos);
			default:
				return null;
		}
	}

	// for reading
	public static InputStream getInputStream() throws FileNotFoundException
	{
		switch (_storageOption)
		{
			case STDOUT:
				return null;
			case FILE:
				FileInputStream fos = new FileInputStream(new File(
						_context.getExternalFilesDir(null), _fileName));
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
