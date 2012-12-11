package com.petrifiednightmares.singularityChess.logic;

import java.util.Set;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.GameException;
import com.petrifiednightmares.singularityChess.InvalidMoveException;
import com.petrifiednightmares.singularityChess.logging.MoveLogger;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;
import com.petrifiednightmares.singularityChess.pieces.Bishop;
import com.petrifiednightmares.singularityChess.pieces.King;
import com.petrifiednightmares.singularityChess.pieces.Knight;
import com.petrifiednightmares.singularityChess.pieces.Pawn;
import com.petrifiednightmares.singularityChess.pieces.Queen;
import com.petrifiednightmares.singularityChess.pieces.Rook;
import com.petrifiednightmares.singularityChess.ui.GameDrawable;
import com.petrifiednightmares.singularityChess.ui.GameUI;
import com.petrifiednightmares.singularityChess.ui.Preferences;
import com.petrifiednightmares.singularityChess.ui.SUI;

public class Game extends GameDrawable
{

	AbstractPiece[] whitePieces;
	AbstractPiece[] blackPieces;

	private boolean isWhiteTurn;

	Board _board;

	AbstractPiece selectedPiece, checkingPiece;
	Set<Square> selectedPieceMoves;

	private GameUI _gui;
	MoveLogger ml;

	private String whiteName, blackName;

	private Bitmap _borderBitmap;
	private Canvas _borderCanvas;

	public Game(GameDrawingPanel drawingPanel)
	{
		super(drawingPanel);
		ml = new MoveLogger();

		isWhiteTurn = true;
		whitePieces = new AbstractPiece[16];
		blackPieces = new AbstractPiece[16];

		whiteName = "White";
		blackName = "Black";

		setupBorder();
	}

	public void setBoard(Board board)
	{
		this._board = board;
		initializePieces(whitePieces, true);
		initializePieces(blackPieces, false);
	}

	public void setGameUI(GameUI gui)
	{
		this._gui = gui;
		gui.setTurnName(whiteName, isWhiteTurn);
	}

	private void initializePieces(AbstractPiece[] piecesArray, boolean isWhite)
	{
		// 8 pawns
		System.arraycopy(Pawn.makePawns(this, isWhite), 0, piecesArray, 0, 8);

		// 2 rooks
		System.arraycopy(Rook.makeRooks(this, isWhite), 0, piecesArray, 8, 2);

		// 2 bishops
		System.arraycopy(Bishop.makeBishops(this, isWhite), 0, piecesArray, 10, 2);

		// 2 knights
		System.arraycopy(Knight.makeKnights(this, isWhite), 0, piecesArray, 12, 2);

		// king
		System.arraycopy(King.makeKings(this, isWhite), 0, piecesArray, 14, 1);

		// queen
		System.arraycopy(Queen.makeQueens(this, isWhite), 0, piecesArray, 15, 1);

	}

	public Set<Square> selectAndGetMoves(Square s) throws GameException
	{
		AbstractPiece p = s.getPiece();
		if (p == null)
			return null;

		// if piece belongs to current player
		if (p.isWhite() == isWhiteTurn())
		{
			// select the piece
			selectedPiece = p;
			p.select();
			// remember the selected piece's moves
			return selectedPieceMoves = selectedPiece.getMoves();
		} else
		{
			// otherwise unselect all pieces.
			if (selectedPiece != null)
				selectedPiece.unselect();
			selectedPiece = null;
			selectedPieceMoves = null;

			// but return the moves anyway.
			return selectedPiece.getMoves();
		}
	}

	public boolean canMakeMove(Square target)
	{
		if (selectedPieceMoves != null && !selectedPieceMoves.contains(target)
				&& target.isHighlighted())
		{
			selectedPieceMoves.add(target);
		}

		return selectedPiece != null && selectedPieceMoves != null
				&& (selectedPieceMoves.contains(target))
				&& selectedPiece.isWhite() == isWhiteTurn()
				&& (!target.hasPiece() || target.getPiece().isCapturable());
	}

	public boolean makeMove(Square target) throws InvalidMoveException
	{
		if (_gui.PROMPT_WAITING)
			return false;

		if (selectedPiece != null && selectedPieceMoves.contains(target))
		{
			Square sourceLocation = selectedPiece.getLocation();

			AbstractPiece capturedPiece = selectedPiece.makeMove(target);

			// check if king is in check
			if (!checkMoveValidity())
			{
				unmakeMove(capturedPiece, selectedPiece, target, sourceLocation);
				if (checkingPiece != null)
					select(checkingPiece);
				return false;
			}
			String actionLog;
			// log to movelogger
			if (capturedPiece == null)
			{
				actionLog = ml.addMove(selectedPiece, sourceLocation, target);
			} else
			{
				actionLog = ml.addMove(selectedPiece, sourceLocation, target, capturedPiece);
			}
			// TODO, display actionLog
			System.out.println(actionLog);

			if (checkPostMoveConditions())
			{
				finishMove();
			}
		} else
		{
			throw new InvalidMoveException(
					"Invalid Move: Either piece not selected or illegal move");
		}
		return true;
	}

	private void finishMove()
	{
		if (!_gui.PROMPT_WAITING)
		{
			playPieceSounds();
			switchTurns();
			unselect();
		}
	}

	private void unmakeMove(AbstractPiece capturedPiece, AbstractPiece actor,
			Square destinationLocation, Square sourceLocation)
	{
		destinationLocation.removePiece();
		if (capturedPiece != null)
			capturedPiece.revive(destinationLocation);
		actor.setLocation(sourceLocation);

	}

	private void playPieceSounds()
	{
		if (!Preferences.MUTE)
		{
			SUI.pieceSound.start();
		}
	}

	private void switchTurns()
	{
		isWhiteTurn = !isWhiteTurn;
		_gui.setTurnName(isWhiteTurn() ? whiteName : blackName, isWhiteTurn());
	}

	private boolean checkMoveValidity()
	{
		// Make sure king not in check
		AbstractPiece[] enemyPieces = isWhiteTurn() ? blackPieces : whitePieces;
		for (AbstractPiece p : enemyPieces)
		{
			if (p.isAlive())
			{
				if (p.checkingKing())
				{
					gdp.displayMessage(p + " on square " + p.getLocation() + " is checking king");
					checkingPiece = p;
					return false;
				}
			}
		}

		return true;
	}

	private boolean checkPostMoveConditions()
	{
		// check to see if theres a check, a checkmate, or a pawn can get
		// promoted.

		if (selectedPiece instanceof Pawn)
		{
			if (Board.isEndOfFile(selectedPiece.getLocation()))
			{
				// can be promoted
				promptPromotion(selectedPiece);
				return false;
			}
		}

		return true;
	}

	private void promptPromotion(AbstractPiece piece)
	{
		select(piece);
		_gui.openPromotionDialog(piece.isWhite());
	}

	// replace the piece in our array so the old one gets thrown away.
	private void replacePiece(AbstractPiece oldPiece, AbstractPiece newPiece)
	{
		if (newPiece.isWhite())
		{
			int index = findPiece(selectedPiece);
			if (index != -1)
				whitePieces[index] = newPiece;
		} else
		{
			int index = findPiece(selectedPiece);
			if (index != -1)
				blackPieces[index] = newPiece;
		}
	}

	private int findPiece(AbstractPiece p)
	{
		if (p.isWhite())
		{
			for (int i = 0; i < whitePieces.length; i++)
			{
				if (whitePieces[i].equals(p))
				{
					return i;
				}
			}
		} else
		{
			for (int i = 0; i < blackPieces.length; i++)
			{
				if (blackPieces[i].equals(p))
				{
					return i;
				}
			}
		}
		return -1;
	}

	public void promotePiece(AbstractPiece.PieceType pieceType)
	{
		if (selectedPiece instanceof Pawn)
		{
			AbstractPiece newPiece = ((Pawn) (selectedPiece)).promote(pieceType);
			replacePiece(selectedPiece, newPiece);
			finishMove();
		} else
		{
			gdp.displayMessage(selectedPiece + " at location: " + selectedPiece.getLocation()
					+ " is not a pawn yet was attempted to be promoted");
		}
	}

	public boolean isTurn()
	{
		if (selectedPiece != null)
			return isWhiteTurn() == selectedPiece.isWhite();
		else
			return false;
	}

	private void setupBorderShadow()
	{
		// Set up variables

		// x component of the center of the circle
		int h = SUI.WIDTH / 2;
		// y component of the center of the circle
		int k = SUI.HEIGHT_CENTER;

		// left side of the rectangle
		int x = SUI.WIDTH / 2 - 4 * SUI.CIRCLE_RADIUS_DIFFERENCE - SUI.BORDER_WIDTH;
		// radius of circle
		int r = 6 * SUI.CIRCLE_RADIUS_DIFFERENCE + SUI.BORDER_WIDTH;

		// define a rectangle that circumscribes the circle
		RectF circle = new RectF(h - r, k - r, h + r, k + r);

		Path p = new Path();
		// draw a line that goes from the bottom left to the top left of the
		// shape
		p.moveTo(x, (float) (k + Math.sqrt(-(h * h) + 2 * h * x + r * r - (x * x))));
		p.lineTo(x, (float) (k - Math.sqrt(-(h * h) + 2 * h * x + r * r - (x * x))));

		// calculate the angle that the top left of the shape represents in the
		// circle
		float angle = (float) Math.toDegrees(Math.atan(Math.sqrt(-(h * h) + 2 * h * x + r * r
				- (x * x))
				/ (h - x)));

		// draw an arc from the top left of shape to top right of shape
		p.arcTo(circle, 180 + angle, (180 - angle * 2));

		// the x component of the right side of the shape
		x = SUI.WIDTH / 2 + 4 * SUI.CIRCLE_RADIUS_DIFFERENCE + SUI.BORDER_WIDTH;

		// draw line from top right to bottom right
		p.lineTo(x, (float) (k + Math.sqrt(-(h * h) + 2 * h * x + r * r - (x * x))));

		// draw arc back from bottom right to bottom left.
		p.arcTo(circle, angle, (180 - angle * 2));

		// draw the path onto the canvas
		_borderCanvas.drawPath(p, SUI.borderShadowPaint);
	}

	private void setupBorder()
	{
		_borderBitmap = Bitmap.createBitmap(SUI.WIDTH, SUI.HEIGHT, Bitmap.Config.ARGB_8888);
		_borderCanvas = new Canvas(_borderBitmap);

		setupBorderShadow();

		_borderCanvas.save();
		_borderCanvas.clipRect(SUI.PADDING, 0, SUI.WIDTH - SUI.PADDING, SUI.HEIGHT);
		_borderCanvas.drawCircle(SUI.WIDTH / 2, SUI.HEIGHT_CENTER, 6 * SUI.CIRCLE_RADIUS_DIFFERENCE
				+ SUI.BORDER_WIDTH, SUI.borderPaint);
		_borderCanvas.restore();
	}

	public void onDraw(Canvas canvas)
	{
		if (NEEDS_REDRAW)
		{
			NEEDS_REDRAW = false;
			// draw background
			canvas.drawBitmap(GameDrawingPanel.background, 0, 0, null);
			canvas.drawBitmap(_borderBitmap, 0, 0, null);
		}
	}

	public void redraw()
	{
		NEEDS_REDRAW = true;
	}

	public Board getBoard()
	{
		return _board;
	}

	public GameDrawingPanel getDrawingPanel()
	{
		return gdp;
	}

	// **********************************Saving and restoring
	// *********************************************************//

	// *********************************UI related
	// shits***********************************/

	public boolean onClick(int x, int y)
	{
		return false;
	}

	public void select(AbstractPiece piece)
	{
		if (!_gui.PROMPT_WAITING)
		{
			try
			{
				_board.unhighlightAllSquares();
				selectedPiece = piece;
				selectedPieceMoves = piece.getMoves();
				_board.highlightMoves(selectedPieceMoves);
				_board.select(piece.getLocation());
				checkingPiece = null;
			} catch (GameException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void unselect()
	{
		if (!_gui.PROMPT_WAITING)
		{
			_board.unhighlightAllSquares();
			selectedPiece = null;
			selectedPieceMoves = null;
		}
	}

	public boolean isWhiteTurn()
	{
		return isWhiteTurn;
	}

}
