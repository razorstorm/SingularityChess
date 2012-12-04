package com.petrifiednightmares.singularityChess.ui;

import android.graphics.Canvas;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;

public abstract class GameDrawable
{
	protected boolean NEEDS_REDRAW;
	protected GameDrawingPanel gdp;
	
	public GameDrawable(GameDrawingPanel gdp)
	{
		this.gdp=gdp;
		NEEDS_REDRAW=true;
	}
	
	public abstract void onDraw(Canvas c);
	
	public abstract boolean onClick(int x, int y);
	
	public abstract void redraw();
}
