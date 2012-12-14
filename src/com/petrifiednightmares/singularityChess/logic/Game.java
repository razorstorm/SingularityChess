package com.petrifiednightmares.singularityChess.logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import android.graphics.Canvas;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.GameException;
import com.petrifiednightmares.singularityChess.InvalidMoveException;
import com.petrifiednightmares.singularityChess.io.GameIO;
import com.petrifiednightmares.singularityChess.io.GameSaveable;
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

	private String whiteName, blackName;


	public Game(GameDrawingPanel drawingPanel)
	{
		super(drawingPanel);

		whitePieces = new AbstractPiece[16];
		blackPieces = new AbstractPiece[16];

		whiteName = "White";
		blackName = "Black";
		
	}

	public void initialize(Board board, GameUI gui)
	{
		this._gui = gui;
		gui.setTurnName(whiteName, isWhiteTurn);

		isWhiteTurn = true;

		this._board = board;
		initializePieces(whitePieces, true);
		initializePieces(blackPieces, false);
	}

	public void resume(Board board, GameUI gui) throws IOException
	{
		this._gui = gui;
		this._board = board;

		GameSaveable gs = new GameSaveable(this);

		GameIO.intentionSaveGame();
		InputStream in = GameIO.getInputStream();

		gs.deserialize(in);
		in.close();

		whitePieces = gs.getWhitePieces();
		blackPieces = gs.getBlackPieces();

		isWhiteTurn = gs.isWhiteTurn();
		this._gui.setMoveLogger(gs.getMoveLogger());
		this._gui.setTurnName(isWhiteTurn() ? whiteName : blackName, isWhiteTurn());
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
			// log to movelogger
			if (capturedPiece == null)
			{
				_gui.recordMove(selectedPiece, sourceLocation, target);
			} else
			{
				_gui.recordMove(selectedPiece, sourceLocation, target, capturedPiece);
			}

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

			saveGame();
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

	public void onDraw(Canvas canvas)
	{
	
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

	public void saveGame()
	{
		try
		{
			GameIO.intentionSaveGame();

			GameSaveable gs = new GameSaveable(isWhiteTurn, whitePieces, blackPieces,
					_gui.getMoveLogger());

			OutputStream out = GameIO.getOutputStream();
			gs.serialize(out);
			out.close();
		} catch (IOException e)
		{
			e.printStackTrace();
			gdp.displayMessage(e.getMessage());
		}
	}
	
	
}
