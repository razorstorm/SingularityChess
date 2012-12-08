package com.petrifiednightmares.singularityChess.ui;

import android.graphics.Canvas;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;

public class GameUI extends GameDrawable
{
	TopBar topBar;
	BottomBar bottomBar;
	public HoverDialog movesDialog, capturesDialog, surrenderDialog, promotionDialog;
	public boolean PROMPT_WAITING;
	public HoverDialog PROMPT;

	public GameUI(GameDrawingPanel drawingPanel)
	{
		super(drawingPanel);
		PROMPT = null;
		this.topBar = new TopBar();
		this.bottomBar = new BottomBar(this,drawingPanel);
		movesDialog = new MovesLogDialog(gdp);

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
	}

	public boolean onClick(int x, int y)
	{

		if (PROMPT != null)
		{
			if(!PROMPT.onClick(x, y))
			{
				PROMPT_WAITING = false;
				PROMPT = null;
			}
		} else
		{
			PROMPT_WAITING = false;
			bottomBar.onClick(x, y);
		}
		
		
		
		return false; //doesnt matter
	}

	@Override
	public void redraw()
	{
		bottomBar.NEEDS_REDRAW = true;
		movesDialog.NEEDS_REDRAW = true;
		topBar.NEEDS_REDRAW = true;		
	}
}
