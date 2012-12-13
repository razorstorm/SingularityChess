package com.petrifiednightmares.singularityChess.ui.dialog;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.ui.GameDrawable;
import com.petrifiednightmares.singularityChess.ui.SUI;

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

		_horizontalPaint = SUI.getHorizontalPaint(_left, _top, _height, _width);
		_verticalPaint = SUI.getVerticalPaint(_left, _top, _height, _width);

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

}