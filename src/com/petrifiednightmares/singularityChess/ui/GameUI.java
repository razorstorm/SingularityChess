package com.petrifiednightmares.singularityChess.ui;

import android.graphics.Canvas;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;
import com.petrifiednightmares.singularityChess.ui.dialog.HoverDialog;
import com.petrifiednightmares.singularityChess.ui.dialog.MovesLogDialog;
import com.petrifiednightmares.singularityChess.ui.dialog.PromotionDialog;

public class GameUI extends GameDrawable
{
	TopBar topBar;
	BottomBar bottomBar;
	public HoverDialog movesDialog, capturesDialog, surrenderDialog, promotionDialog;
	public boolean PROMPT_WAITING;
	public HoverDialog PROMPT;
	private Game _game;

	private static final long WAITING_THRESHOLD = 500; // wait half a second
														// before making prompts
														// clickable
	private long promptTime;

	public GameUI(GameDrawingPanel drawingPanel, Game game)
	{
		super(drawingPanel);
		this._game = game;
		PROMPT = null;
		this.topBar = new TopBar();
		this.bottomBar = new BottomBar(this, drawingPanel);
		movesDialog = new MovesLogDialog(gdp, this);

		promotionDialog = new PromotionDialog(gdp, _game, this);

		PROMPT_WAITING = false;
	}

	public void setTurnName(String turnName, boolean isWhite)
	{
		topBar.setTurnName(turnName, isWhite);
	}

	public void onDraw(Canvas canvas)
	{
		topBar.onDraw(canvas);
		bottomBar.onDraw(canvas);
		movesDialog.onDraw(canvas);
		promotionDialog.onDraw(canvas);
	}

	public boolean onClick(int x, int y)
	{
		if (PROMPT != null)
		{
			PROMPT_WAITING = true;
			if (System.currentTimeMillis() > promptTime + WAITING_THRESHOLD)
				PROMPT.onClick(x, y);
		} else
		{
			PROMPT_WAITING = false;
			bottomBar.onClick(x, y);
		}

		return false; // doesnt matter
	}

	public void closePrompt()
	{
		PROMPT = null;
		PROMPT_WAITING = false;
		gdp.redrawAll();
	}

	@Override
	public void redraw()
	{
		bottomBar.NEEDS_REDRAW = true;
		movesDialog.NEEDS_REDRAW = true;
		topBar.NEEDS_REDRAW = true;
	}

	private void openInteractiveDialog()
	{
		promptTime = System.currentTimeMillis();
	}

	public void openPromotionDialog()
	{
		openInteractiveDialog();
		promotionDialog.display();
		PROMPT = promotionDialog;
		PROMPT_WAITING = true;
	}

	public void promote(AbstractPiece.PieceType pieceType)
	{
		_game.promotePiece(pieceType);
	}
}
