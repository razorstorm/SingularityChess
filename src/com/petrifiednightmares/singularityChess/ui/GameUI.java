package com.petrifiednightmares.singularityChess.ui;

import android.graphics.Canvas;
import android.widget.ScrollView;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.logging.MoveLogger;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.logic.Square;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;
import com.petrifiednightmares.singularityChess.ui.dialog.CapturesDialog;
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
	private MoveLogger _ml;

	private static final long WAITING_THRESHOLD = 500; // wait half a second
														// before making prompts
														// clickable
	private long promptTime;

	public GameUI(GameDrawingPanel drawingPanel, Game game, ScrollView movesView)
	{
		super(drawingPanel);
		this._game = game;
		PROMPT = null;
		this.topBar = new TopBar(drawingPanel);
		this.bottomBar = new BottomBar(this, drawingPanel);
		_ml = new MoveLogger();
		movesDialog = new MovesLogDialog(gdp, this, movesView, _ml);

		promotionDialog = new PromotionDialog(gdp, _game, this);
		
		capturesDialog = new CapturesDialog(gdp,this,_ml);

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
		capturesDialog.onDraw(canvas);
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
		gdp.redraw();
	}

	@Override
	public void redraw()
	{
		bottomBar.redraw();
		movesDialog.redraw();
		topBar.redraw();
	}

	private void openInteractiveDialog()
	{
		promptTime = System.currentTimeMillis();
	}

	public void openPromotionDialog(boolean isWhite)
	{
		openInteractiveDialog();
		((PromotionDialog) promotionDialog).display(isWhite);
		PROMPT = promotionDialog;
		PROMPT_WAITING = true;
	}

	public String recordMove(AbstractPiece actor, Square source, Square destination)
	{
		return _ml.addMove(actor, source, destination);
	}

	public String recordMove(AbstractPiece actor, Square source, Square destination,
			AbstractPiece capturedPiece)
	{
		return _ml.addMove(actor, source, destination, capturedPiece);
	}

	public void promote(AbstractPiece.PieceType pieceType)
	{
		_game.promotePiece(pieceType);
	}

	public MoveLogger getMoveLogger()
	{
		return _ml;
	}

	// for resuming
	public void setMoveLogger(MoveLogger ml)
	{
		this._ml = ml;
		((MovesLogDialog) movesDialog).setMoveLogger(_ml);
		((CapturesDialog) capturesDialog ).setMoveLogger(_ml);
	}

}
