package com.petrifiednightmares.singularityChess.logic;

import java.util.Set;

import android.graphics.Canvas;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.GameException;
import com.petrifiednightmares.singularityChess.InvalidMoveException;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;

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
		board = new Board();
		isWhiteTurn = true;
		initializePieces();
	}

	private void initializePieces()
	{

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
			
			//but return the moves anyway.
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
			throw new InvalidMoveException("Invalid Move: Either piece not selected or illegal move");
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
}
