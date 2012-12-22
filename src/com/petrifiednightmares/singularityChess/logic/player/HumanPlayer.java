package com.petrifiednightmares.singularityChess.logic.player;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;
import com.petrifiednightmares.singularityChess.ui.GameUI;

public class HumanPlayer extends Player
{

	public HumanPlayer(boolean isWhite, String name, GameDrawingPanel gdp, Game g, GameUI gui)
	{
		super(isWhite, name, gdp, g, gui);
	}

	public void winGame()
	{
		_gdp.showFinishPrompt("You win!", "Congratulations, you win!");
	}

	public void loseGame()
	{
		// because machine player doesn't show anything, both will need to be
		// shown
		_gdp.showFinishPrompt("You lose!", "Unfortunately, you lost.");
	}

	public void tieGame()
	{
		_gdp.showFinishPrompt("Stalemate!", "This is stale meat. Should have put it in the fridge.");
	}

	/**
	 * Since this is a human player, this doesn't do anything
	 */
	public void doTurn()
	{

	}

	@Override
	public void promote(AbstractPiece p)
	{
		_game.select(p);

		_gui.openPromotionDialog(p.isWhite());
	}

}
