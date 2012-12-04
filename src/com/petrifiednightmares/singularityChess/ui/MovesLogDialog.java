package com.petrifiednightmares.singularityChess.ui;

import android.graphics.Canvas;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;

public class MovesLogDialog extends HoverDialog
{
	public MovesLogDialog(GameDrawingPanel gdp)
	{
		super(gdp,"Moves", (SUI.HEIGHT / 100) * 10, (SUI.WIDTH / 100) * 5, SUI.WIDTH - 2*((SUI.WIDTH / 100) * 5),
				SUI.HEIGHT - 2*(SUI.HEIGHT / 100) * 10);
	}

	public void onDraw(Canvas c)
	{
		super.onDraw(c);
	}
}
