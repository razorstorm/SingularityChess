package com.petrifiednightmares.singularityChess.logging;

import java.util.LinkedList;

import com.petrifiednightmares.singularityChess.logic.Square;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;

public class MoveLogger
{
	LinkedList<Action> actions = new LinkedList<Action>();

	public String addMove(AbstractPiece actor, Square source, Square destination)
	{
		Action a = new Action(actor, source, destination);
		actions.add(a);
		return a.toString();
	}

	public String addMove(AbstractPiece actor, Square source, Square destination,
			AbstractPiece prisoner)
	{
		Action a = new Action(actor, source, destination, prisoner);
		actions.add(a);
		return a.toString();
	}

}
