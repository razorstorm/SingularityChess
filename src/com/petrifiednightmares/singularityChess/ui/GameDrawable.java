package com.petrifiednightmares.singularityChess.ui;

import android.graphics.Canvas;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;

public abstract class GameDrawable
{
	protected GameDrawingPanel gdp;
	
	public GameDrawable(GameDrawingPanel gdp)
	{
		this.gdp=gdp;
	}
	
	public abstract void onDraw(Canvas c);
	
	public abstract boolean onClick(int x, int y);
	
	public void redraw()
	{
		gdp.redraw();
	}
	
}
