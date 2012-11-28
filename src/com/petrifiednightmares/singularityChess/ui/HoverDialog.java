package com.petrifiednightmares.singularityChess.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;

import com.petrifiednightmares.singularityChess.logic.Game;

public class HoverDialog
{
	protected int _top, _left, _width, _height;
	protected String _title;
	protected RectF _rectf;
	protected Rect _textBounds, _mHeight;
	protected Paint _horizontalPaint, _verticalPaint, _textPaint;
	public boolean NEEDS_REDRAW;

	public HoverDialog(String title, int top, int left, int width, int height)
	{
		this._top = top;
		this._left = left;
		this._width = width;
		this._height = height;
		this._title = title;
		this._rectf = new RectF(_left, SUI.HEIGHT + SUI.UNIT * 10, _left + _width, _top + _height);
		this._horizontalPaint = new Paint();
		this._horizontalPaint.setAntiAlias(true);
		this._horizontalPaint.setShader(new LinearGradient(_left, _top + _height / 2f, _left
				+ _width, _top + _height / 2f, new int[] { Color.argb(200, 30, 21, 9),
				Color.argb(150, 62, 43, 18), Color.argb(150, 62, 43, 18),
				Color.argb(200, 30, 21, 9), Color.argb(20, 255, 255, 255) }, new float[] { 0f,
				0.2f, 0.8f, 1f - 1.5f / _width, 1f }, Shader.TileMode.MIRROR));
		this._horizontalPaint.setAlpha(200);

		this._verticalPaint = new Paint();
		this._verticalPaint.setAntiAlias(true);
		this._verticalPaint.setShader(new LinearGradient(_left + _width / 2f, _top, _left + _width
				/ 2f, _top + _height, new int[] { Color.rgb(30, 21, 9), Color.rgb(62, 43, 18),
				Color.rgb(62, 43, 18), Color.rgb(30, 21, 9), Color.WHITE }, new float[] { 0f,
				0.25f, 0.75f, 1f - 1.5f / _height, 1f }, Shader.TileMode.MIRROR));
		this._verticalPaint.setAlpha(200);

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
		NEEDS_REDRAW = true;
	}

	public void onDraw(Canvas c)
	{
		if (NEEDS_REDRAW)
		{
			NEEDS_REDRAW = false;
			c.drawRoundRect(_rectf, _height * 0.03f, _height * 0.03f, _verticalPaint);
			c.drawRoundRect(_rectf, _height * 0.03f, _height * 0.03f, _horizontalPaint);

			c.drawText(_title, _rectf.left + SUI.UNIT * 5, _rectf.top + SUI.UNIT * 8, _textPaint);

			c.drawRect(_rectf.left, _rectf.top + _mHeight.height() + SUI.UNIT * 10, _left + _width,
					_rectf.top + _mHeight.height() + SUI.UNIT * 10 + 1, _verticalPaint);
		}
	}

	public synchronized void display()
	{
		_rectf.top = _top;
		NEEDS_REDRAW = true;
	}

	public synchronized void hide()
	{
		_rectf.top = SUI.HEIGHT + SUI.UNIT * 10;
		NEEDS_REDRAW = true;
		Game.PROMPT_WAITING = false;
		Game.PROMPT = null;
		Game.REDRAW_ALL = true;
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
}
