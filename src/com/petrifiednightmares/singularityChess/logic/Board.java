package com.petrifiednightmares.singularityChess.logic;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;

import com.petrifiednightmares.singularityChess.GameException;
import com.petrifiednightmares.singularityChess.R;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;
import com.petrifiednightmares.singularityChess.pieces.Bishop;
import com.petrifiednightmares.singularityChess.pieces.Pawn;

public class Board
{
	// hashed by file and rank: "a3" for example.
	private HashMap<String, Square> squares;
	public static final int[] boardRanks = new int[] { 5, 7, 9, 11, 11, 9, 7, 5 };
	private Game _game; //back reference to game
	private Resources _res;
	public static boolean NEEDS_REDRAW=true;

	public Board(Resources res,Game game)
	{
		this._res = res;
		this._game = game;
		squares = new HashMap<String, Square>();
		initializeSquares();
		setupSquaresBitmap();
	}

	private void initializeSquares()
	{
		for (char file = 'a'; file <= 'h'; file++)
		{
			for (int rank = 1; rank <= boardRanks[file - 'a']; rank++)
			{
				squares.put(file + "" + rank, new Square(file, rank));
			}
		}
		linkUpSquares();
	}

	// links up the square's corners and sides
	private void linkUpSquares()
	{
		for (char file = 'a'; file <= 'h'; file++)
		{
			for (int rank = 1; rank <= boardRanks[file - 'a']; rank++)
			{
				int cornersId=0;
				try {
				    Class<R.array> res = R.array.class;
				    Field field = res.getField("corners_"+file+""+rank);
				    cornersId = field.getInt(null);
				}
				catch (Exception e) {
				    Log.e("MyTag", "Failure to get corners id.", e);
				    e.printStackTrace();
				}
				
				String[] corners = _res.getStringArray(cornersId);
				Square[] cornerSquares = new Square[4];
				for(int i =0; i<4;i++)
				{
					if(corners[i] != "0")
						cornerSquares[i] = squares.get(corners[i]);
					else
						cornerSquares[i] = null;
				}
				
				squares.get(file +""+rank).setCorners(cornerSquares);
				
				
				int sidesId=0;
				try {
				    Class<R.array> res = R.array.class;
				    Field field = res.getField("sides_"+file+""+rank);
				    sidesId = field.getInt(null);
				}
				catch (Exception e) {
				    Log.e("MyTag", "Failure to get sides id.", e);
				    e.printStackTrace();
				}
				
				String[] sides = _res.getStringArray(sidesId);
				Square[] sideSquares = new Square[4];
				for(int i =0; i<4;i++)
				{
					if(sides[i] != "0")
						sideSquares[i] = squares.get(sides[i]);
					else
						sideSquares[i] = null;
				}
				
				squares.get(file +""+rank).setSides(sideSquares);
			}
		}
	}

	private void setupSquaresBitmap()
	{
		// Have to draw from outwards in
		for (char file = 'a'; file <= 'd'; file++)
		{
			for (int rank = 1; rank <= boardRanks[file - 'a'] / 2; rank++)
			{
				getSquares().get(file + "" + rank).setUpBitMap();
			}
			for (int rank = boardRanks[file - 'a']; rank >= boardRanks[file - 'a'] / 2 + 1; rank--)
			{
				getSquares().get(file + "" + rank).setUpBitMap();
			}
		}
	
		for (char file = 'h'; file >= 'e'; file--)
		{
			for (int rank = 1; rank <= boardRanks[file - 'a'] / 2; rank++)
			{
				getSquares().get(file + "" + rank).setUpBitMap();
			}
			for (int rank = boardRanks[file - 'a']; rank >= boardRanks[file - 'a'] / 2 + 1; rank--)
			{
				getSquares().get(file + "" + rank).setUpBitMap();
			}
		}
	}

	public Set<Square> getSideMovements(AbstractPiece piece, boolean limit) throws GameException
	{
		Set<Square> moves = new HashSet<Square>();
		Square startSquare = piece.getLocation();
		boolean isWhite = piece.isWhite();

		Square currSquare = startSquare;
		Square prevSquare = startSquare;

		Square[] sides = startSquare.getSides();

		for (int i = 0; i < 4; i++)
		{
			currSquare = sides[i];
			prevSquare = startSquare;
			if (currSquare == null)
			{
				continue;
			}
			AbstractPiece obstructingPiece = currSquare.getPiece();
			// if the square is empty or can be captured
			if (obstructingPiece == null)
			{
				moves.add(currSquare);
			} else if (obstructingPiece.isWhite() != isWhite && obstructingPiece.isCapturable())
			{
				moves.add(currSquare);
				continue;
			} else
			{
				continue;
			}

			if (!limit) // if not limited, keep searching
			{
				while (true)
				{
					// move to next side
					Square newSquare = currSquare.getNextSide(prevSquare);

					prevSquare = currSquare;
					currSquare = newSquare;

					if (currSquare == null)
					{
						break;
					}

					// find out if the next square is movable to
					obstructingPiece = currSquare.getPiece();

					if (obstructingPiece == null)// if empty, add the square
													// as a move
					{
						moves.add(currSquare);
					}
					// if can capture, add as move, but stop searching
					else if (obstructingPiece.isWhite() != isWhite
							&& obstructingPiece.isCapturable())
					{
						moves.add(currSquare);
						break;
					}
					// if either cannot be captured, or is your own piece,
					// stop searching.
					else
					{
						break;
					}

				}
			}
		}

		return moves;
	}

	public Set<Square> getCornerMovements(AbstractPiece piece, boolean limit) throws GameException
	{
		Set<Square> moves = new HashSet<Square>();
		Square startSquare = piece.getLocation();
		boolean isWhite = piece.isWhite();

		Square currSquare = startSquare;
		Square prevSquare = startSquare;

		Square[] corners = startSquare.getCorners();

		for (int i = 0; i < 4; i++)
		{
			prevSquare = startSquare;
			currSquare = corners[i];
			if (currSquare == null)
			{
				continue;
			}
			AbstractPiece obstructingPiece = currSquare.getPiece();
			// if the square is empty or can be captured
			if (obstructingPiece == null)
			{
				moves.add(currSquare);
			} else if (obstructingPiece.isWhite() != isWhite && obstructingPiece.isCapturable())
			{
				moves.add(currSquare);
				continue;
			} else
			{
				continue;
			}

			if (!limit) // if not limited, keep searching
			{
				while (true)
				{
					// move to next side
					Square newSquare = currSquare.getNextCorner(prevSquare);

					prevSquare = currSquare;
					currSquare = newSquare;

					if (currSquare == null)
					{
						break;
					}

					// find out if the next square is movable to
					obstructingPiece = currSquare.getPiece();

					if (obstructingPiece == null)// if empty, add the square
													// as a move
					{
						moves.add(currSquare);
					}
					// if can capture, add as move, but stop searching
					else if (obstructingPiece.isWhite() != isWhite
							&& obstructingPiece.isCapturable())
					{
						moves.add(currSquare);
						break;
					}
					// if either cannot be captured, or is your own piece,
					// stop searching.
					else
					{
						break;
					}
				}
			}
		}

		return moves;
	}

	// 2 first moves, empassant n shit
	public Set<Square> getPawnMoves(AbstractPiece piece) throws GameException
	{
		Set<Square> moves = new HashSet<Square>();
		Square startSquare = piece.getLocation();
		boolean isWhite = piece.isWhite();
		boolean canJump = ((Pawn) piece).canJump();

		Square[] sides = startSquare.getSides();
		for (int i = 0; i < 4; i++)
		{
			Square next = sides[i];
			if (next.getFile() == startSquare.getFile()
					&& next.getRank() == startSquare.getRank() + (isWhite ? 1 : -1))
			{
				AbstractPiece obstructingPiece = next.getPiece();
				// if the square is empty
				if (obstructingPiece == null)
				{
					moves.add(startSquare);
					if (canJump)
					{
						next = next.getNextSide(startSquare);

						obstructingPiece = next.getPiece();
						// if the square is empty
						if (obstructingPiece == null)
						{
							moves.add(next);
						}
					}
				}
			}
		}
		return moves;
	}

	// en passant n shit
	public Set<Square> getPawnCaptures(AbstractPiece piece)
	{
		Set<Square> moves = new HashSet<Square>();
		Square startSquare = piece.getLocation();
		boolean isWhite = piece.isWhite();

		Square[] corners = startSquare.getCorners();
		for (int i = 0; i < 4; i++)
		{
			Square next = corners[i];

			if (next.getFile() != startSquare.getFile()
					&& next.getRank() == startSquare.getRank() + (isWhite ? 1 : -1))
			{
				AbstractPiece obstructingPiece = next.getPiece();
				// if the square is capturable
				// TODO en passant...
				if (obstructingPiece.isWhite() != isWhite && obstructingPiece.isCapturable())
				{
					moves.add(startSquare);
				}
			}
		}
		return moves;
	}

	public Set<Square> getKnightMoves(AbstractPiece piece) throws GameException
	{
		Set<Square> moves = new HashSet<Square>();
		Square startSquare = piece.getLocation();
		boolean isWhite = piece.isWhite();

		Square currSquare = startSquare;
		Square prevSquare = startSquare;

		Square[] sides = startSquare.getSides();

		for (int i = 0; i < 4; i++)
		{
			currSquare = sides[i];
			if (currSquare == null)
			{
				continue;
			}
			Square nextSquare = currSquare.getNextSide(prevSquare);
			prevSquare = currSquare;
			currSquare = nextSquare;

			if (currSquare == null)
			{
				continue;
			}
			Square[] potentialMoves = currSquare.getAdjacentSides(prevSquare);
			for (Square s : potentialMoves)
			{
				AbstractPiece obstructingPiece = s.getPiece();
				// if the square is empty
				if (obstructingPiece == null
						|| (obstructingPiece.isWhite() != isWhite && obstructingPiece
								.isCapturable()))
				{
					moves.add(s);
				}
			}
		}
		return moves;
	}

	public void onDraw(Canvas canvas)
	{
		if(NEEDS_REDRAW)
		{
			canvas.drawBitmap(Square._squareBitMap, 0, 0, null);
			NEEDS_REDRAW = false;
		}
		drawSquares(canvas);
	}

	private void drawSquares(Canvas canvas)
	{
		for (char file = 'a'; file <= 'h'; file++)
		{
			for (int rank = 1; rank <= boardRanks[file - 'a']; rank++)
			{
				squares.get(file + "" + rank).onDraw(canvas);
			}
		}
	}


	public void onClick(int x, int y)
	{
		//TODO cycle through Squares to do collision detection
		//then figure out what to do depending on what the square's stats are.
		for(String key: squares.keySet())
		{
			Square s = squares.get(key);
			if (s.containsPoint(x, y))
			{
				this.unhighlightAllSquares();
				Bishop testingRook = new Bishop(this._game, s, true);
				s.addPiece(testingRook);
				try
				{
					highlightMoves(testingRook);
				} catch (GameException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
			}
		}
	}
	
	private void unhighlightAllSquares()
	{
		for(String key: squares.keySet())
		{
			squares.get(key).unhighlight();
		}
		NEEDS_REDRAW=true;
	}
	
	public void highlightMoves(AbstractPiece p) throws GameException
	{
		Set<Square> moves = p.getMoves();
		for(Square s: moves)
		{
			s.highlight();
		}
	}
	
	
	public HashMap<String, Square> getSquares()
	{
		return squares;
	}
}
