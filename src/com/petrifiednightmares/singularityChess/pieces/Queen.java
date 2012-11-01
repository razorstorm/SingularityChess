package com.petrifiednightmares.singularityChess.pieces;

import java.util.Set;
import java.util.TreeSet;

import android.graphics.BitmapFactory;

import com.petrifiednightmares.singularityChess.GameException;
import com.petrifiednightmares.singularityChess.R;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.logic.Square;

public class Queen extends AbstractPiece
{
	public Queen(Game game,Square location, boolean isWhite)
	{
		super(game,location,isWhite,BitmapFactory.decodeResource(
				game.getDrawingPanel().getResources(), R.drawable.queen));
	}
	public Set<Square> getMoves() throws GameException
	{
		Set<Square> moves = new TreeSet<Square>();
		moves.addAll(game.getBoard().getSideMovements(this, false));
		moves.addAll(game.getBoard().getCornerMovements(this, false));
		return moves;
	}
	

}
