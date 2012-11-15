package com.petrifiednightmares.singularityChess.logging;

import com.petrifiednightmares.singularityChess.logic.Square;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;
import com.petrifiednightmares.singularityChess.pieces.Pawn;

public class Action
{
	private AbstractPiece _actor;
	private Square _source, _destination;
	private boolean _isCapture;
	private AbstractPiece _prisoner;

	public Action(AbstractPiece actor, Square source, Square destination)
	{
		this._actor = actor;
		this._source = source;
		this._destination = destination;
		this._isCapture = false;
	}

	public Action(AbstractPiece actor, Square source, Square destination, AbstractPiece prisoner)
	{
		this._actor = actor;
		this._source = source;
		this._destination = destination;
		this._isCapture = true;
		this._prisoner = prisoner;
	}

	public AbstractPiece getActor()
	{
		return _actor;
	}

	public Square getSource()
	{
		return _source;
	}

	public Square getDestination()
	{
		return _destination;
	}

	public boolean isCapture()
	{
		return _isCapture;
	}

	public AbstractPiece get_prisoner()
	{
		return _prisoner;
	}

	public String toString()
	{
		if (_isCapture)
		{
			if (_actor instanceof Pawn)
			{
				return _actor.getIcon() + _source.getFile() + "x" + _destination.getFile()
						+ _destination.getRank();
			} else
			{
				return _actor.getIcon() + "x" + _destination.getFile() + _destination.getRank();
			}
		} else
		{
			// Pawn will still get figurine
			return _actor.getIcon() + _destination.getFile() + _destination.getRank(); 
		}
	}
}
