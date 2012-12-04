package com.petrifiednightmares.singularityChess.ui;

import android.graphics.Canvas;
import android.graphics.Rect;

public class TopBar
{
	public boolean NEEDS_REDRAW;
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

			c.drawRect(0, 0, SUI.WIDTH, SUI.TOP_BAR_BOTTOM,
					SUI.topBarTexturePaint);
			c.drawRect(0, 0, SUI.WIDTH, SUI.TOP_BAR_BOTTOM,
					SUI.topBarPaint);

			SUI.turnNamePaint.getTextBounds(_turnName, 0, _turnName.length(), bounds);

			c.drawCircle(30, SUI.TOP_BAR_BOTTOM / 2, bounds
					.height() / 2, _isWhite ? SUI.turnNameWhitePaint
					: SUI.turnNameBlackPaint);

			c.drawText(_turnName, 30 + bounds.height(), SUI.TOP_BAR_BOTTOM / 2 + bounds.height() / 2,
					SUI.turnNamePaint);
		}
	}
}
