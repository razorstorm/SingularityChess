package com.petrifiednightmares.singularityChess.ui;

import android.graphics.Canvas;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;

public class TopBar
{
	private static boolean NEEDS_REDRAW;
	private String _turnName;

	public TopBar(String turnName)
	{
		setTurnName(turnName);
	}

	public void setTurnName(String turnName)
	{
		NEEDS_REDRAW = true;
		this._turnName = turnName + "'s turn";
	}

	public void onDraw(Canvas c)
	{
		if (NEEDS_REDRAW)
		{
			NEEDS_REDRAW = false;

			c.drawRect(0, 0, GameDrawingPanel.WIDTH, GameDrawingPanel.TOP_BAR_BOTTOM,
					GameDrawingPanel.topBarPaint);

			c.drawText(_turnName, 0, 0, GameDrawingPanel.turnNamePaint);
		}
	}
}
