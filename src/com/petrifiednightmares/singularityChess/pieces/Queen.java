package com.petrifiednightmares.singularityChess.pieces;

import java.util.HashSet;
import java.util.Set;

import com.petrifiednightmares.singularityChess.GameException;
import com.petrifiednightmares.singularityChess.R;
import com.petrifiednightmares.singularityChess.logic.Board;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.logic.Square;
import com.petrifiednightmares.singularityChess.ui.SUI;
import com.petrifiednightmares.singularityChess.utilities.SingularBitmapFactory;

public class Queen extends AbstractPiece
{
	public Queen(Game game, Square location, boolean isWhite)
	{
		super(game, location, isWhite, isWhite ? "\u2655" : "\u265B", SingularBitmapFactory
				.buildSingularScaledBitmap(game.getDrawingPanel().getResources(),
						isWhite ? R.drawable.queen : R.drawable.black_queen, SUI.PIECE_SIZE,
						SUI.PIECE_SIZE), PieceType.Queen);
	}

	public Set<Square> getMoves() throws GameException
	{
		Set<Square> moves = new HashSet<Square>();
		moves.addAll(game.getBoard().getSideMovements(this, false));
		moves.addAll(game.getBoard().getCornerMovements(this, false));
		return moves;
	}

	public static AbstractPiece[] makeQueens(Game game, boolean isWhite)
	{
		AbstractPiece[] queens = new AbstractPiece[2];

		int rank = isWhite ? 1 : Board.boardRanks['d' - 'a'];

		Square location1 = game.getBoard().getSquares().get("d" + rank);
		Queen r1 = new Queen(game, location1, isWhite);
		queens[0] = r1;
		location1.addPiece(r1);

		return queens;
	}

	public String toString()
	{
		return "Queen";
	}
}
