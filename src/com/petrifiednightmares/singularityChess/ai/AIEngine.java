package com.petrifiednightmares.singularityChess.ai;

import java.util.Set;

import com.petrifiednightmares.singularityChess.GameException;
import com.petrifiednightmares.singularityChess.logic.Board;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.logic.Square;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;


public class AIEngine {

	public static final int	EASY = 1, MEDUIM = 2, HOLYSHIT = 3;
	
	private int	aiLevel;

	private Game _game;
	private Board _board;	
	private AbstractPiece _selectingPiece;
	private Square _target;
	private boolean	isWhiteTurn;

	private AbstractPiece[]	_whitePieces;
	private AbstractPiece[]	_blackPieces;
	
	public AIEngine(Game game)
	{
		this._game = game;
		this._board = game.getBoard();
		// we should get the AI level from game type, need to fix this later. 
		this.aiLevel = AIEngine.MEDUIM;
		this.isWhiteTurn = game.isWhiteTurn();
		this._whitePieces = game.getWhitePiece();
		this._blackPieces = game.getBlackPiece();
	}
	
	public AbstractPiece getSelectingPiece()
	{
		return this._selectingPiece;
	}
	
	public Square getTarget()
	{
		return this._target;
	}
	
	public void calcNextMove()
	{
		AbstractPiece[] pieces = this.isWhiteTurn ? _whitePieces: _blackPieces;
		for (AbstractPiece p : pieces)
		{
			if (p.isAlive())
			{
				try
				{
					Set<Square> moves = p.getMoves();
					for (Square target : moves)
					{
						this._selectingPiece = p; 
						this._target = target;
						return ;						
					}
				}
				catch (GameException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
