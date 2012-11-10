package com.petrifiednightmares.singularityChess.logic;

import java.util.Set;

import android.graphics.Canvas;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.GameException;
import com.petrifiednightmares.singularityChess.InvalidMoveException;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;
import com.petrifiednightmares.singularityChess.pieces.Bishop;
import com.petrifiednightmares.singularityChess.pieces.King;
import com.petrifiednightmares.singularityChess.pieces.Knight;
import com.petrifiednightmares.singularityChess.pieces.Pawn;
import com.petrifiednightmares.singularityChess.pieces.Queen;
import com.petrifiednightmares.singularityChess.pieces.Rook;

public class Game
{
	GameDrawingPanel drawingPanel;
	Board board;

	AbstractPiece[] whitePieces;
	AbstractPiece[] blackPieces;

	boolean isWhiteTurn;

	AbstractPiece selectedPiece;
	Set<Square> selectedPieceMoves;

	public Game(GameDrawingPanel drawingPanel)
	{
		this.drawingPanel = drawingPanel;
		board = new Board(drawingPanel.getResources(),this);
		isWhiteTurn = true;
		whitePieces = new AbstractPiece[16];
		blackPieces = new AbstractPiece[16];
//		initializePieces(whitePieces, true);
//		initializePieces(blackPieces, false);
		initializeDebug(); // for testing purposes
	}
	private void initializeDebug()
	{
		Square location = board.getSquares().get("c3");
		Rook testingRook = new Rook(this, location, true);
		Square location2 = board.getSquares().get("f7");
		Rook testingRook2 = new Rook(this, location2, true);
		try
		{
			board.highlightMoves(testingRook);
			board.highlightMoves(testingRook2);
		} catch (GameException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		System.arraycopy(King.makeKings(this, isWhite), 0, piecesArray, 13, 1);
		
		// queen
		System.arraycopy(Queen.makeQueens(this, isWhite), 0, piecesArray, 14, 1);
		

	}

	public Set<Square> selectAndGetMoves(Square s) throws GameException
	{
		AbstractPiece p = s.getPiece();
		if (p == null)
			return null;

		// if piece belongs to current player
		if (p.isWhite() == isWhiteTurn)
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

	public AbstractPiece makeMove(Square target) throws InvalidMoveException
	{
		if (selectedPiece != null && selectedPieceMoves.contains(target))
		{
			return selectedPiece.makeMove(target);
		} else
		{
			throw new InvalidMoveException(
					"Invalid Move: Either piece not selected or illegal move");
		}
	}

	public void onDraw(Canvas canvas)
	{
		board.onDraw(canvas);

	}

	public Board getBoard()
	{
		return board;
	}

	public GameDrawingPanel getDrawingPanel()
	{
		return drawingPanel;
	}
	
	//*********************************UI related shits***********************************/
	
	public void onClick(int x, int y)
	{
		board.onClick(x,y);		
	}
	
}
