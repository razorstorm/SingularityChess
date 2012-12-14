package com.petrifiednightmares.singularityChess.logic;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.GameException;
import com.petrifiednightmares.singularityChess.InvalidMoveException;
import com.petrifiednightmares.singularityChess.R;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;
import com.petrifiednightmares.singularityChess.pieces.Pawn;
import com.petrifiednightmares.singularityChess.ui.GameDrawable;
import com.petrifiednightmares.singularityChess.ui.SUI;

public class Board extends GameDrawable
{
	// hashed by file and rank: "a3" for example.
	private HashMap<String, Square> squares;
	public static final int[] boardRanks = new int[] { 5, 7, 9, 11, 11, 9, 7, 5 };
	public static final int[] realBoardRanks = new int[] { 5, 7, 9, 12, 12, 9, 7, 5 };
	private Game _game; // back reference to game
	private Resources _res;


	public Board(GameDrawingPanel gdp, Game game)
	{
		super(gdp);
		this._res = gdp.getResources();
		this._game = game;
		squares = new HashMap<String, Square>();

		initializeSquares();
		setupSquaresBitmap();
	}

	private void initializeSquares()
	{
		for (char file = 'a'; file <= 'h'; file++)
		{
			for (int rank = 1; rank <= realBoardRanks[file - 'a']; rank++)
			{
				squares.put(file + "" + rank, new Square(file, rank, this, gdp));
			}
		}
		linkUpSquares();
	}

	// links up the square's corners and sides
	private void linkUpSquares()
	{
		for (char file = 'a'; file <= 'h'; file++)
		{
			for (int rank = 1; rank <= realBoardRanks[file - 'a']; rank++)
			{
				int cornersId = 0;
				try
				{
					Class<R.array> res = R.array.class;
					Field field = res.getField("corners_" + file + "" + rank);
					cornersId = field.getInt(null);
				} catch (Exception e)
				{
					Log.e("MyTag", "Failure to get corners id.", e);
					e.printStackTrace();
				}

				String[] corners = _res.getStringArray(cornersId);
				Square[] cornerSquares = new Square[4];
				for (int i = 0; i < 4; i++)
				{
					if (corners[i] != "0")
						cornerSquares[i] = squares.get(corners[i]);
					else
						cornerSquares[i] = null;
				}

				squares.get(file + "" + rank).setCorners(cornerSquares);

				int sidesId = 0;
				try
				{
					Class<R.array> res = R.array.class;
					Field field = res.getField("sides_" + file + "" + rank);
					sidesId = field.getInt(null);
				} catch (Exception e)
				{
					Log.e("MyTag", "Failure to get sides id.", e);
					e.printStackTrace();
				}

				String[] sides = _res.getStringArray(sidesId);
				Square[] sideSquares = new Square[4];
				for (int i = 0; i < 4; i++)
				{
					if (sides[i] != "0")
						sideSquares[i] = squares.get(sides[i]);
					else
						sideSquares[i] = null;
				}

				squares.get(file + "" + rank).setSides(sideSquares);
			}
		}
	}

	private void setupSquaresBitmap()
	{

		// test if the cached resource exists
		// int test = gdp.getResources().getIdentifier("board_bitmap",
		// "drawable",
		// gdp.getContext().getPackageName());
		// if (test != 0)
		// {
		// _background =
		// SingularBitmapFactory.buildScaledBitmap(gdp.getResources(), test,
		// SUI.WIDTH, SUI.HEIGHT);
		// } else
		// {
		// _background = Bitmap.createBitmap(SUI.WIDTH, SUI.HEIGHT,
		// Bitmap.Config.ARGB_8888);
		Canvas backgroundCanvas = _game.getBackgroundCanvas();

		// Have to draw from outwards in
		for (char file = 'a'; file <= 'd'; file++)
		{
			for (int rank = 1; rank <= boardRanks[file - 'a'] / 2; rank++)
			{
				getSquares().get(file + "" + rank).setUpBitMap(backgroundCanvas);
			}
			for (int rank = boardRanks[file - 'a']; rank >= boardRanks[file - 'a'] / 2 + 1; rank--)
			{
				getSquares().get(file + "" + rank).setUpBitMap(backgroundCanvas);
			}
		}

		for (char file = 'h'; file >= 'e'; file--)
		{
			for (int rank = 1; rank <= boardRanks[file - 'a'] / 2; rank++)
			{
				getSquares().get(file + "" + rank).setUpBitMap(backgroundCanvas);
			}
			for (int rank = boardRanks[file - 'a']; rank >= boardRanks[file - 'a'] / 2 + 1; rank--)
			{
				getSquares().get(file + "" + rank).setUpBitMap(backgroundCanvas);
			}
		}

		backgroundCanvas.save();
		backgroundCanvas.clipRect(SUI.WIDTH / 2 - 4 * SUI.CIRCLE_RADIUS_DIFFERENCE, 0, SUI.WIDTH
				/ 2 + 4 * SUI.CIRCLE_RADIUS_DIFFERENCE, SUI.HEIGHT);
		backgroundCanvas.drawCircle(SUI.WIDTH / 2, SUI.HEIGHT_CENTER,
				6 * SUI.CIRCLE_RADIUS_DIFFERENCE, SUI.boardLightingPaint);
		backgroundCanvas.restore();
		// }
	}

	public Set<Square> getSideMovements(AbstractPiece piece, boolean limit) throws GameException
	{
		Set<Square> moves = new HashSet<Square>();
		Square startSquare = piece.getLocation();
		boolean isWhite = piece.isWhite();

		if (startSquare.getTag().compareTo("d6") == 0)
		{
			piece.makeMove(squares.get("d12"));
			moves.addAll(this.getSideMovements(piece, limit));
			piece.makeMove(squares.get("d6"));
		}

		if (startSquare.getTag().compareTo("e6") == 0)
		{
			piece.makeMove(squares.get("e12"));
			moves.addAll(this.getSideMovements(piece, limit));
			piece.makeMove(squares.get("e6"));
		}

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
			} else if (obstructingPiece.isWhite() != isWhite)
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
					else if (obstructingPiece.isWhite() != isWhite)
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
			} else if (obstructingPiece.isWhite() != isWhite)
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
					else if (obstructingPiece.isWhite() != isWhite)
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

	// 2 first moves
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
			if (next != null && next.getFile() == startSquare.getFile()
					&& next.getRank() == startSquare.getRank() + (isWhite ? 1 : -1))
			{
				AbstractPiece obstructingPiece = next.getPiece();
				// if the square is empty
				if (obstructingPiece == null)
				{
					moves.add(next);
					if (canJump)
					{
						sides = next.getSides();
						for (i = 0; i < 4; i++)
						{
							Square jumpnext = sides[i];
							if (jumpnext != null && jumpnext.getFile() == startSquare.getFile()
									&& jumpnext.getRank() == next.getRank() + (isWhite ? 1 : -1))
							{
								obstructingPiece = jumpnext.getPiece();
								// if the square is empty
								if (obstructingPiece == null)
								{
									moves.add(jumpnext);
								}
							}
						}
					}
				}
			}
		}
		return moves;
	}

	// TODO fix captures
	public Set<Square> getPawnCaptures(AbstractPiece piece) throws GameException
	{
		Set<Square> moves = new HashSet<Square>();
		Square startSquare = piece.getLocation();
		boolean isWhite = piece.isWhite();

		Square[] sides = startSquare.getSides();

		Square next = null;
		for (int i = 0; i < 4; i++)
		{
			next = sides[i];
			if (next != null && next.getFile() == startSquare.getFile()
					&& next.getRank() == startSquare.getRank() + (isWhite ? 1 : -1))
			{
				break;
			}
		}
		Square[] corners;
		if (next != null)
		{
			corners = next.getSides();

			for (int i = 0; i < 2; i++)
			{
				next = corners[i];

				if (next != null && next.getFile() != startSquare.getFile() && next.hasPiece())
				{
					AbstractPiece obstructingPiece = next.getPiece();
					// if the square is capturable
					if (obstructingPiece.isWhite() != isWhite)
					{
						moves.add(next);
					}
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
			prevSquare = startSquare;
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
				if (s == null)
					continue;
				AbstractPiece obstructingPiece = s.getPiece();
				// if the square is empty
				if (obstructingPiece == null || (obstructingPiece.isWhite() != isWhite))
				{
					moves.add(s);
				}
			}
		}
		return moves;
	}

	public void onDraw(Canvas canvas)
	{
		// draw the squares
		// canvas.drawBitmap(Square.squareBitMap, 0, 0, null);
		// Draw lighting overlay

		// draw special overlays on the squares
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

	public boolean onClick(int x, int y)
	{
		/*
		 * // testing purpose only for(String key: squares.keySet()) { Square s
		 * = squares.get(key); s.removePiece(); }
		 * 
		 * for(String key: squares.keySet()) {
		 * 
		 * if (key.compareTo("d12")==0 || key.compareTo("e12")==0) continue;
		 * Square s = squares.get(key); if (s.containsPoint(x, y)) {
		 * this.unhighlightAllSquares(); Rook testPiece = new Rook(this._game,
		 * s, true); s.addPiece(testPiece); _game.select(s.getPiece()); break; }
		 * }
		 */

		// cycle through Squares to do collision detection
		// then figure out what to do depending on what the square's stats are.
		for (String key : squares.keySet())
		{
			if (key.compareTo("d12") == 0 || key.compareTo("e12") == 0)
				continue;
			Square s = squares.get(key);
			if (s.onClick(x, y))
			{
				if (_game.canMakeMove(s))
				{
					try
					{
						_game.makeMove(s);
					} catch (InvalidMoveException e)
					{
						e.printStackTrace();
					}
				} else if (s.hasPiece())
				{
					_game.select(s.getPiece());
				} else
				{
					// if didn't hit anything
					_game.unselect();
				}

				return true;
			}
		}

		return false;
	}

	void unhighlightAllSquares()
	{
		for (String key : squares.keySet())
		{
			squares.get(key).unhighlight();
		}
		redraw();
	}

	public void select(Square s)
	{
		s.select();
	}

	public void highlightMoves(Set<Square> moves) throws GameException
	{
		for (Square s : moves)
		{
			s.highlight();
		}
	}

	public HashMap<String, Square> getSquares()
	{
		return squares;
	}

	public Game getGame()
	{
		return _game;
	}

	public static boolean isEndOfFile(Square location)
	{
		if (location.getRank() == 1)
			return true;
		else if (location.getRank() == boardRanks[location.getFile() - 'a'])
			return true;
		else
			return false;
	}

	@Override
	public void redraw()
	{
		super.redraw();
		for (char file = 'a'; file <= 'h'; file++)
		{
			for (int rank = 1; rank <= boardRanks[file - 'a']; rank++)
			{
				squares.get(file + "" + rank).redraw();
			}
		}
	}
}
