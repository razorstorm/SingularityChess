package com.petrifiednightmares.singularityChess.logging;

import java.util.LinkedList;

import com.petrifiednightmares.singularityChess.logic.Square;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;

public class MoveLogger
{
	private LinkedList<Action> _actions;

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
		return a.toString();
	}

	public MoveLogger()
	{
		this._actions = new LinkedList<Action>();
	}

	public MoveLogger(LinkedList<Action> actions)
	{
		this._actions = actions;
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
