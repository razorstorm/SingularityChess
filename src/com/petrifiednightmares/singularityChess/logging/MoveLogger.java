package com.petrifiednightmares.singularityChess.logging;

import java.util.LinkedList;

import com.petrifiednightmares.singularityChess.logic.Square;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;

public class MoveLogger
{
	private LinkedList<Action> _actions;

	// these are the prisoners
	private LinkedList<String> _capturedWhitePieces;
	private LinkedList<String> _capturedBlackPieces;

	public MoveLogger()
	{
		this._actions = new LinkedList<Action>();
		_capturedWhitePieces = new LinkedList<String>();
		_capturedBlackPieces = new LinkedList<String>();
	}

	public MoveLogger(LinkedList<Action> actions, LinkedList<String> capturedWhitePieces,
			LinkedList<String> capturedBlackPieces)
	{
		this._actions = actions;
		this._capturedWhitePieces = capturedWhitePieces;
		this._capturedBlackPieces = capturedBlackPieces;
	}

	public String addMove(AbstractPiece actor, Square source, Square destination)
	{
		Action a = new Action(actor, source, destination);
		_actions.add(a);
		return a.toString();
	}

	public String addMove(AbstractPiece actor, Square source, Square destination,
			AbstractPiece prisoner)
	{
		Action a = new Action(actor, source, destination, prisoner);
		_actions.add(a);

		if (prisoner.isWhite())
		{
			_capturedWhitePieces.add(prisoner.getIcon());
		} else
		{
			_capturedBlackPieces.add(prisoner.getIcon());
		}

		return a.toString();
	}

	public LinkedList<String> getCapturedWhitePieces()
	{
		return _capturedWhitePieces;
	}

	public LinkedList<String> getCapturedBlackPieces()
	{
		return _capturedBlackPieces;
	}
	
	public String generateCapturedWhitePiecesString()
	{
		StringBuilder sb = new StringBuilder();
		for (String a : _capturedWhitePieces)
		{
			sb.append(a +" ");
		}
		return sb.toString();
	}
	
	public String generateCapturedBlackPiecesString()
	{
		StringBuilder sb = new StringBuilder();
		for (String a : _capturedBlackPieces)
		{
			sb.append(a +" ");
		}
		return sb.toString();
	}

	public LinkedList<Action> getMoves()
	{
		return _actions;
	}

	public int getNumMoves()
	{
		return _actions.size();
	}

	public String returnLineSeparatedMoves()
	{
		int i = 1;
		StringBuilder sb = new StringBuilder();
		for (Action a : _actions)
		{
			sb.append(i + ": " + a.toString() + "\n");
			i++;
		}
		return sb.toString();
	}
}
