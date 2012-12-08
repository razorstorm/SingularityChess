package com.petrifiednightmares.singularityChess.ui.dialog;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.ui.GameDrawable;
import com.petrifiednightmares.singularityChess.ui.SUI;

public class HoverDialog extends GameDrawable
{
	protected int _top, _left, _width, _height;
	protected String _title;
	protected RectF _rectf;
	protected Rect _textBounds, _mHeight;
	protected Paint _backgroundPaint, _textPaint;
	protected boolean _shown;

	public HoverDialog(GameDrawingPanel gdp, String title, int top, int left, int width, int height)
	{
		super(gdp);
		this._shown = false;
		this._top = top;
		this._left = left;
		this._width = width;
		this._height = height;
		this._title = title;
		this._rectf = new RectF(_left, _top, _left + _width, _top + _height);

		this._backgroundPaint = new Paint();
		this._backgroundPaint.setAntiAlias(true);
		this._backgroundPaint.setColor(Color.rgb(62, 43, 18));
		this._backgroundPaint.setAlpha(200);
		this._backgroundPaint.setShadowLayer(20, 50, 50, Color.argb(100, 0, 0, 0));

		this._textPaint = new Paint();
		this._textPaint.setAntiAlias(true);
		this._textPaint.setColor(Color.WHITE);
		this._textPaint.setTextSize(40);
		this._textPaint.setAlpha(255);
		this._textPaint.setShadowLayer(15, 0, 0, Color.argb(70, 255, 255, 255));

		_textBounds = new Rect();
		_textPaint.getTextBounds(_title, 0, _title.length(), _textBounds);
		_mHeight = new Rect();
		_textPaint.getTextBounds("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", 0, 1,
				_mHeight);
	}

	public void onDraw(Canvas c)
	{
		if (NEEDS_REDRAW)
		{
			NEEDS_REDRAW = false;
			if (_shown)
			{
				c.drawRoundRect(_rectf, _height * 0.01f, _height * 0.01f, _backgroundPaint);

				c.drawText(_title, _rectf.left + SUI.UNIT * 5, _rectf.top + SUI.UNIT * 8,
						_textPaint);

				c.drawRect(_rectf.left, _rectf.top + _mHeight.height() + SUI.UNIT * 10, _left
						+ _width, _rectf.top + _mHeight.height() + SUI.UNIT * 10 + 1,
						_backgroundPaint);
			}
		}
	}

	public synchronized void display()
	{
		_shown = true;
		redraw();
	}

	public synchronized void hide()
	{
		_shown = false;
		redraw();
		gdp.redrawAll();
	}

	public boolean onClick(int x, int y)
	{
		boolean click = _rectf.contains(x, y);
		if (!click)
		{
			hide();
		}
		return click;
	}

	@Override
	public void redraw()
	{
		NEEDS_REDRAW = true;
	}
}
