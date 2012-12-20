package com.petrifiednightmares.singularityChess.ui;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;

public class TopBar extends GameDrawable
{
	private String	_turnName;
	Rect			bounds;
	private boolean	_isWhite;

	public TopBar(GameDrawingPanel gdp)
	{
		super(gdp);
		bounds = new Rect();
	}

	public void setTurnName(String turnName, boolean isWhite)
	{
		this._isWhite = isWhite;
		this._turnName = turnName + "'s turn";
		redraw();
	}

	public void onDraw(Canvas c)
	{

		c.drawRect(0, 0, SUI.WIDTH, SUI.TOP_BAR_BOTTOM, SUI.topBarTexturePaint);
		c.drawRect(0, 0, SUI.WIDTH, SUI.TOP_BAR_BOTTOM, SUI.topBarPaint);

		// SUI.turnNamePaint.getTextBounds(_turnName, 0, _turnName.length(),
		// bounds);
		SUI.turnNamePaint.getTextBounds("m", 0, 1, bounds);

		c.drawCircle(30, SUI.TOP_BAR_BOTTOM / 2, 12, _isWhite ? SUI.turnNameWhitePaint
				: SUI.turnNameBlackPaint);

		c.drawText(_turnName, 35 + bounds.height(), SUI.TOP_BAR_BOTTOM / 2
				+ (bounds.bottom - bounds.top) / 2, SUI.turnNamePaint);
	}

	@Override
	public boolean onClick(int x, int y)
	{
		return false;
	}
}
