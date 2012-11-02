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
	
	
	public static AbstractPiece[] makeQueens(Game game, boolean isWhite)
	{
		AbstractPiece[] queens = new AbstractPiece[2];

		int rank = isWhite ? 1 : 8;
		
		Square location1 =  game.getBoard().getSquares().get("d" + rank);
		Queen r1 = new Queen(game,location1,isWhite);
		queens[0]=r1;
		location1.addPiece(r1);
		
		return queens;
	}

}
