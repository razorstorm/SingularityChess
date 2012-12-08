package com.petrifiednightmares.singularityChess.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;

public class ActionButton
{
	private int _top, _left, _width, _height;
	private String _title;
	private RectF _rectf;
	private Rect _textBounds, _mHeight;
	private Paint _horizontalPaint, _verticalPaint, _iconPaint;
	private Bitmap _icon;

	public ActionButton(Bitmap icon, int top, int left, int width, int height)
	{
		this._icon = icon;
		initialize(top, left, width, height);
	}

	public ActionButton(String title, int top, int left, int width, int height)
	{
		initialize(top, left, width, height);
		this._title = title;


		_textBounds = new Rect();
		_iconPaint.getTextBounds(_title, 0, _title.length(), _textBounds);
		_mHeight = new Rect();
		_iconPaint.getTextBounds("m", 0, 1, _mHeight);
	}

	private void initialize(int top, int left, int width, int height)
	{
		this._top = top;
		this._left = left;
		this._width = width;
		this._height = height;
		this._rectf = new RectF(_left, _top, _left + _width, _top + _height);
		this._horizontalPaint = new Paint();
		this._horizontalPaint.setAntiAlias(true);
		this._horizontalPaint.setShader(new LinearGradient(_left, _top + _height / 2f, _left
				+ _width, _top + _height / 2f, new int[] { Color.argb(200, 30, 21, 9),
				Color.argb(150, 62, 43, 18), Color.argb(150, 62, 43, 18),
				Color.argb(200, 30, 21, 9), Color.argb(20, 255, 255, 255) }, new float[] { 0f,
				0.2f, 0.8f, 1f - 1.5f / _width, 1f }, Shader.TileMode.MIRROR));
		this._horizontalPaint.setAlpha(150);

		this._verticalPaint = new Paint();
		this._verticalPaint.setAntiAlias(true);
		this._verticalPaint.setShader(new LinearGradient(_left + _width / 2f, _top, _left + _width
				/ 2f, _top + _height, new int[] { Color.rgb(30, 21, 9), Color.rgb(62, 43, 18),
				Color.rgb(62, 43, 18), Color.rgb(30, 21, 9), Color.WHITE }, new float[] { 0f,
				0.25f, 0.75f, 1f - 1.5f / _height, 1f }, Shader.TileMode.MIRROR));
		this._verticalPaint.setAlpha(150);

		this._iconPaint = new Paint();
		this._iconPaint.setAntiAlias(true);
		this._iconPaint.setColor(Color.WHITE);
		this._iconPaint.setTextSize(20);
		this._iconPaint.setAlpha(120);
		this._iconPaint.setShadowLayer(15, 0, 0, Color.argb(70, 255, 255, 255));
	}

	public void onDraw(Canvas c)
	{
		c.drawRoundRect(_rectf, _height * 0.1f, _height * 0.1f, _verticalPaint);
		c.drawRoundRect(_rectf, _height * 0.1f, _height * 0.1f, _horizontalPaint);

		if (_title != null)
			c.drawText(_title, _left + _width / 2 - _textBounds.width() / 2, _top + _height / 2
					+ _mHeight.height() / 2, _iconPaint);
		else
			c.drawBitmap(_icon, _left + _width / 2 - _icon.getWidth() / 2,
					_top + _height / 2- _icon.getHeight() / 2, _iconPaint);
	}

	public boolean onClick(int x, int y)
	{
		return x > _left && x <= _left + _width && y > _top && y < _top + _height;
	}

	public int get_top()
	{
		return _top;
	}

	public void set_top(int _top)
	{
		this._top = _top;
	}

	public int get_left()
	{
		return _left;
	}

	public void set_left(int _left)
	{
		this._left = _left;
	}

	public int get_width()
	{
		return _width;
	}

	public void set_width(int _width)
	{
		this._width = _width;
	}

	public int get_height()
	{
		return _height;
	}

	public void set_height(int _height)
	{
		this._height = _height;
	}

	public String get_title()
	{
		return _title;
	}

	public void set_title(String _title)
	{
		this._title = _title;
	}

}
