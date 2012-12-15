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

public class Bishop extends AbstractPiece
{
	public Bishop(Game game, Square location, boolean isWhite)
	{
		super(game, location, isWhite, isWhite ? "\u2657" : "\u265D", SingularBitmapFactory
				.buildSingularScaledBitmap(game.getDrawingPanel().getResources(),
						isWhite ? R.drawable.bishop : R.drawable.black_bishop, SUI.PIECE_SIZE,
						SUI.PIECE_SIZE), PieceType.Bishop);
	}

	public Set<Square> getMoves() throws GameException
	{
		Set<Square> moves = new HashSet<Square>();
		moves.addAll(game.getBoard().getCornerMovements(this, false));
		return moves;
	}

	public static AbstractPiece[] makeBishops(Game game, boolean isWhite)
	{
		AbstractPiece[] bishops = new AbstractPiece[2];

		int rank = isWhite ? 1 : Board.boardRanks['c' - 'a'];

		Square location1 = game.getBoard().getSquares().get("c" + rank);
		Bishop r1 = new Bishop(game, location1, isWhite);
		bishops[0] = r1;
		location1.addPiece(r1);

		Square location2 = game.getBoard().getSquares().get("f" + rank);
		Bishop r2 = new Bishop(game, location2, isWhite);
		bishops[1] = r2;
		location2.addPiece(r2);

		return bishops;
	}

	public String toString()
	{
		return "Bishop";
	}

}
