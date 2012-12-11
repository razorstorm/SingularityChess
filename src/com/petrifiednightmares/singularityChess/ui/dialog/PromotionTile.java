package com.petrifiednightmares.singularityChess.ui.dialog;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.ui.GameDrawable;

public class PromotionTile extends GameDrawable
{

	private int _top, _left, _width, _height;
	private String _title;
	private RectF _rectf;
	private Rect _textBounds, _mHeight;
	private Paint _iconPaint;
	private Bitmap _whiteIcon, _blackIcon, _icon;
	private boolean _shown;

	private Paint _horizontalPaint, _verticalPaint;

	public PromotionTile(GameDrawingPanel gdp, String title, Bitmap whiteIcon, Bitmap blackIcon,
			int top, int left, int width, int height)
	{
		super(gdp);
		this._shown = false;
		this._whiteIcon = whiteIcon;
		this._blackIcon = blackIcon;

		this._icon = this._whiteIcon;

		this._top = top;
		this._left = left;
		this._width = width;
		this._title = title;
		this._height = height;
		this._rectf = new RectF(_left, top, _left + _width, _top + _height);

		int darkColor = Color.argb(200, 24, 17, 7);
		int lightColor = Color.argb(100, 62, 43, 18);
		int darkColor2 = Color.rgb(19, 14, 6);
		int lightColor2 = Color.argb(150, 62, 43, 18);

		this._horizontalPaint = new Paint();
		this._horizontalPaint.setAntiAlias(true);
		this._horizontalPaint.setShader(new LinearGradient(_left, _top + _height / 2f, _left
				+ _width, _top + _height / 2f, new int[] { darkColor, lightColor, lightColor,
				darkColor, Color.argb(20, 255, 255, 255) }, new float[] { 0f, 0.2f, 0.8f,
				1f - 1.5f / _width, 1f }, Shader.TileMode.MIRROR));
		this._horizontalPaint.setAlpha(150);

		this._verticalPaint = new Paint();
		this._verticalPaint.setAntiAlias(true);
		this._verticalPaint.setShader(new LinearGradient(_left + _width / 2f, _top, _left + _width
				/ 2f, _top + _height, new int[] { darkColor2, lightColor2, lightColor2, darkColor2,
				Color.WHITE }, new float[] { 0f, 0.25f, 0.75f, 1f - 1.5f / _height, 1f },
				Shader.TileMode.MIRROR));
		this._verticalPaint.setAlpha(150);

		this._iconPaint = new Paint();
		this._iconPaint.setAntiAlias(true);
		this._iconPaint.setColor(Color.WHITE);
		this._iconPaint.setTextSize(30);
		this._iconPaint.setShadowLayer(10, 0, 0, Color.argb(255, 0, 0, 0));

		_textBounds = new Rect();
		_iconPaint.getTextBounds(_title, 0, _title.length(), _textBounds);
		_mHeight = new Rect();
		_iconPaint.getTextBounds("m", 0, 1, _mHeight);
	}

	@Override
	public void onDraw(Canvas c)
	{
		if (NEEDS_REDRAW)
		{
			NEEDS_REDRAW = false;
			if (_shown)
			{

				c.drawRoundRect(_rectf, _width * 0.05f, _width * 0.05f, _verticalPaint);
				c.drawRoundRect(_rectf, _width * 0.05f, _width * 0.05f, _horizontalPaint);

				c.drawBitmap(_icon, _left + _width / 2 - _icon.getWidth() / 2, _rectf.top
						+ (int) (_height * 0.7) / 2 - _icon.getHeight() / 2, _iconPaint);

				c.drawText(_title, _left + _width / 2 - _textBounds.width() / 2, (int) (_rectf.top
						+ _height * 0.7 + (_height * 0.3) / 2 + _mHeight.height() / 2), _iconPaint);

			}
		}
	}

	@Override
	public boolean onClick(int x, int y)
	{
		return _rectf.contains(x, y);
	}

	public void show(boolean isWhite)
	{
		_shown = true;
		if (isWhite)
			_icon = _whiteIcon;
		else
			_icon = _blackIcon;
	}

	public void hide()
	{
		_shown = false;
	}

	@Override
	public void redraw()
	{
		NEEDS_REDRAW = true;
	}

}