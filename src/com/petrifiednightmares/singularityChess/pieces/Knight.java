package com.petrifiednightmares.singularityChess.pieces;

import java.util.HashSet;
import java.util.Set;

import com.petrifiednightmares.singularityChess.GameException;
import com.petrifiednightmares.singularityChess.logic.Board;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.logic.Square;

public class Knight extends AbstractPiece
{
	public Knight(Game game,Square location, boolean isWhite)
	{
		super(game,location,isWhite, isWhite?"♘":"♞");
	}

	public Set<Square> getMoves() throws GameException
	{
		Set<Square> moves = new HashSet<Square>();
		moves.addAll(game.getBoard().getKnightMoves(this));
		return moves;
	}
	
	public static AbstractPiece[] makeKnights(Game game, boolean isWhite)
	{
		AbstractPiece[] knights = new AbstractPiece[2];
		
		int rank = isWhite ? 1 : Board.boardRanks['b'-'a'];
		
		Square location1 =  game.getBoard().getSquares().get("b" + rank);
		Knight r1 = new Knight(game,location1,isWhite);
		knights[0]=r1;
		location1.addPiece(r1);
		
		Square location2 =  game.getBoard().getSquares().get("g" + rank);
		Knight r2 = new Knight(game,location2,isWhite);
		knights[1]=r2;
		location2.addPiece(r2);
		
		return knights;
	}
	
	public String toString()
	{
		return "Knight";
	}

}
