package com.petrifiednightmares.singularityChess.ui;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;

public class TopBar
{
	private static boolean NEEDS_REDRAW;
	private String _turnName;
	Rect bounds;
	private boolean _isWhite;

	public TopBar(String turnName)
	{
		setTurnName(turnName, true);
		bounds = new Rect();
	}

	public void setTurnName(String turnName, boolean isWhite)
	{
		NEEDS_REDRAW = true;
		this._isWhite = isWhite;
		this._turnName = turnName + "'s turn";
	}

	public void onDraw(Canvas c)
	{
		if (NEEDS_REDRAW)
		{
			NEEDS_REDRAW = false;

			c.drawRect(0, 0, GameDrawingPanel.WIDTH, GameDrawingPanel.TOP_BAR_BOTTOM,
					GameDrawingPanel.topBarTexturePaint);
			c.drawRect(0, 0, GameDrawingPanel.WIDTH, GameDrawingPanel.TOP_BAR_BOTTOM,
					GameDrawingPanel.topBarPaint);

			GameDrawingPanel.turnNamePaint.getTextBounds(_turnName, 0, _turnName.length(), bounds);

			c.drawCircle(30, GameDrawingPanel.TOP_BAR_BOTTOM / 2, bounds
					.height() / 2, _isWhite ? GameDrawingPanel.turnNameWhitePaint
					: GameDrawingPanel.turnNameBlackPaint);

			c.drawText(_turnName, 30 + bounds.height(), GameDrawingPanel.TOP_BAR_BOTTOM / 2 + bounds.height() / 2,
					GameDrawingPanel.turnNamePaint);
		}
	}
}
