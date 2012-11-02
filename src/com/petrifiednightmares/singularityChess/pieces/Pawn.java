package com.petrifiednightmares.singularityChess.pieces;

import java.util.Set;
import java.util.TreeSet;

import android.graphics.BitmapFactory;

import com.petrifiednightmares.singularityChess.GameException;
import com.petrifiednightmares.singularityChess.R;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.logic.Square;

public class Pawn extends AbstractPiece
{
	private boolean canJump = true;
	private boolean jumped = false;

	public Pawn(Game game, Square location, boolean isWhite)
	{
		super(game, location, isWhite, BitmapFactory.decodeResource(game.getDrawingPanel()
				.getResources(), R.drawable.pawn));
	}

	public Set<Square> getMoves() throws GameException
	{
		Set<Square> moves = new TreeSet<Square>();
		moves.addAll(game.getBoard().getPawnMoves(this));// 2 first moves, en
															// passant n shit
		// sigh
		moves.addAll(game.getBoard().getPawnCaptures(this));

		return moves;
	}

	public AbstractPiece makeMove(Square target)
	{
		canJump = false;
		// TODO figure out how to turn off jumped if one turn has passed.
		// if (target.isPawnJump())// check rank to see if its 2 "forward"
		// {
		// jumped = true;
		// } else
		// {
		// jumped = false;
		// }
		return super.makeMove(target);
	}

	public boolean canJump()
	{
		return canJump;
	}

	public boolean isJumped()
	{
		return jumped;
	}

	public static AbstractPiece[] makePawns(Game game, boolean isWhite)
	{
		AbstractPiece[] pawns = new AbstractPiece[8];

		// 8 pawns
		for (int i = 0; i < 8; i++)
		{
			char file = (char) ('a' + i);
			int rank = isWhite ? 2 : 7;
			
			Square location = game.getBoard().getSquares().get(file + "" + rank);
			Pawn p = new Pawn(game, location, isWhite);
			pawns[i] = p;
			location.addPiece(p);
		}
		
		return pawns;
	}
}
