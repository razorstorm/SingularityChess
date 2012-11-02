package com.petrifiednightmares.singularityChess.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Region;
import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.GameException;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;

public class Square
{
	private Square[] corners;
	private Square[] sides;
	private final char file; // rooks are on file a and h
	private final int rank; // white on 1 black on 8
	static Bitmap _squareBitMap;
	private static Canvas _squareCanvas;
	private boolean _isWhite;
	private Paint _paint;

	private AbstractPiece piece = null;

	public Square(char file, int rank)
	{
		this.file = file;
		this.rank = rank;
	}

	public Square(Square[] corners, Square[] sides, char file, int rank)
	{
		this.corners = corners;
		this.sides = sides;
		this.file = file;
		this.rank = rank;
		
	}

	public void setUpBitMap()
	{

		// if parity is the same, it's white
		_isWhite = ((file - 'a') + 1) % 2 == rank % 2;
		_paint = GameDrawingPanel.darkPaint;
		if (_isWhite)
		{
			_paint = GameDrawingPanel.lightPaint;
		}
		_paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		
		//Create bitmaps if don't exist yet

		if (Square._squareBitMap == null)
			Square._squareBitMap = Bitmap.createBitmap(GameDrawingPanel.WIDTH,
					GameDrawingPanel.HEIGHT, Bitmap.Config.ARGB_8888);
		if (Square._squareCanvas == null)
			Square._squareCanvas = new Canvas(_squareBitMap);

		
		//draw onto bitmap
		Square._squareCanvas.save();

		if (file <= 'd')
		{
			Square._squareCanvas.clipRect(GameDrawingPanel.WIDTH / 2 - ('d' - file + 1) * 12
					* GameDrawingPanel.UNIT, 0, GameDrawingPanel.WIDTH / 2 - ('d' - file) * 12
					* GameDrawingPanel.UNIT, GameDrawingPanel.HEIGHT, Region.Op.INTERSECT);
		} else
		{
			Square._squareCanvas.clipRect(GameDrawingPanel.WIDTH / 2 + (file - 'e') * 12
					* GameDrawingPanel.UNIT, 0, GameDrawingPanel.WIDTH / 2 + (file - 'e' + 1) * 12
					* GameDrawingPanel.UNIT, GameDrawingPanel.HEIGHT, Region.Op.INTERSECT);
		}

		Square._squareCanvas.drawCircle(GameDrawingPanel.WIDTH / 2, GameDrawingPanel.HEIGHT / 2,
				(1 + fileOutwards() + rankOutwards()) * 12 * GameDrawingPanel.UNIT, _paint);

		Square._squareCanvas.restore();

	}

	private int fileOutwards()
	{
		if (file <= 'd')
			return 'd' - file;
		else
			return file - 'e';
	}

	private int rankOutwards()
	{

		if (rank >= Board.boardRanks[file - 'a'] / 2 + 1)
		{
			return rank - (Board.boardRanks[file - 'a'] / 2 + 1);
		} else
		{
			return Board.boardRanks[file - 'a'] / 2 + 1 - rank;
		}

	}

	public void onDraw(Canvas canvas)
	{
//		canvas.drawBitmap(_squareBitMap, 0, 0, null);
		//TODO draw the pieces, if any
	}

	public void removePiece()
	{
		this.piece = null;
	}

	public void addPiece(AbstractPiece piece)
	{
		this.piece = piece;
	}

	public AbstractPiece getPiece()
	{
		return this.piece;
	}

	public Square getNextSide(Square firstSide) throws GameException
	{
		for (int i = 0; i < 4; i++)
		{
			Square s = sides[i];
			if (s.equals(firstSide))
			{
				return sides[(i + 2) % 4]; // might be null
			}
		}
		throw new GameException("given side " + firstSide + " is not adjacent to this square");
	}

	// This is for knights, it gets the elbow shaped ones.
	public Square[] getAdjacentSides(Square firstSide) throws GameException
	{
		for (int i = 0; i < 4; i++)
		{
			Square s = sides[i];
			if (s.equals(firstSide))
			{
				return new Square[] { sides[(i + 1) % 4], sides[(i - 1) % 4] };
			}
		}
		throw new GameException("given side " + firstSide + " is not adjacent to this square");
	}

	public Square getNextCorner(Square firstCorner) throws GameException
	{
		for (int i = 0; i < 4; i++)
		{
			Square c = corners[i];
			if (c.equals(firstCorner))
			{
				return corners[(i + 2) % 4]; // might be null
			}
		}
		throw new GameException("given corner " + firstCorner + " is not adjacent to this square");
	}

	@Override
	public String toString()
	{
		return "Square: " + file + "" + rank;
	}

	public Square[] getCorners()
	{
		return corners;
	}

	public Square[] getSides()
	{
		return sides;
	}

	public char getFile()
	{
		return file;
	}

	public int getRank()
	{
		return rank;
	}
}
