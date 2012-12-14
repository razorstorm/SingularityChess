package com.petrifiednightmares.singularityChess.utilities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.SparseArray;

public class SingularBitmapFactory
{
	private static SparseArray<Bitmap> bitmaps = new SparseArray<Bitmap>();

	public static Bitmap buildBitmap(Resources r, int id)
	{
		Bitmap b = bitmaps.get(id, null);
		if (b != null)
		{
			return b;
		} else
		{
			b = BitmapFactory.decodeResource(r, id);
			bitmaps.put(id, b);
			return b;
		}
	}

	public static Bitmap buildScaledBitmap(Resources r, int id, int newWidth, int newHeight)
	{
		Bitmap b = bitmaps.get(id, null);
		if (b != null && b.getWidth() == newWidth && b.getHeight() == newHeight)
		{
			return b;
		} else
		{
			b = BitmapFactory.decodeResource(r, id);

			float scaleWidth = ((float) newWidth) / b.getWidth();
			float scaleHeight = ((float) newHeight) / b.getHeight();
			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleHeight);

			b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);

			bitmaps.put(id, b);
			return b;
		}

	}
	
	
	public static Bitmap buildScaledBitmap(Resources r, String f, int newWidth, int newHeight)
	{
		Bitmap b = bitmaps.get(f.hashCode(), null);
		if (b != null && b.getWidth() == newWidth && b.getHeight() == newHeight)
		{
			return b;
		} else
		{
			b = BitmapFactory.decodeFile(f);

			float scaleWidth = ((float) newWidth) / b.getWidth();
			float scaleHeight = ((float) newHeight) / b.getHeight();
			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleHeight);

			b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);

			bitmaps.put(f.hashCode(), b);
			return b;
		}

	}

}
