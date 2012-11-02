package com.petrifiednightmares.singularityChess.pieces;

import java.util.Set;
import java.util.TreeSet;

import android.graphics.BitmapFactory;

import com.petrifiednightmares.singularityChess.GameException;
import com.petrifiednightmares.singularityChess.R;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.logic.Square;

public class Bishop extends AbstractPiece
{
	public Bishop(Game game,Square location, boolean isWhite)
	{
		super(game,location,isWhite,BitmapFactory.decodeResource(
				game.getDrawingPanel().getResources(), R.drawable.bishop));
	}
	public Set<Square> getMoves() throws GameException
	{
		Set<Square> moves = new TreeSet<Square>();
		moves.addAll(game.getBoard().getCornerMovements(this, false));
		return moves;
	}
	

	
	public static AbstractPiece[] makeBishops(Game game, boolean isWhite)
	{
		AbstractPiece[] bishops = new AbstractPiece[2];

		int rank = isWhite ? 1 : 8;
		
		Square location1 =  game.getBoard().getSquares().get("c" + rank);
		Bishop r1 = new Bishop(game,location1,isWhite);
		bishops[0]=r1;
		location1.addPiece(r1);
		
		Square location2 =  game.getBoard().getSquares().get("f" + rank);
		Bishop r2 = new Bishop(game,location2,isWhite);
		bishops[1]=r2;
		location2.addPiece(r2);
		
		return bishops;
	}
	
}
