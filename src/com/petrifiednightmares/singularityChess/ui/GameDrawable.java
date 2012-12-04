package com.petrifiednightmares.singularityChess.ui;

import android.graphics.Canvas;

public abstract class GameDrawable
{
	public boolean NEEDS_REDRAW=false;
	
	public abstract void onDraw(Canvas c);
	
	public abstract boolean onClick(int x, int y);
}
