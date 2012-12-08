package com.petrifiednightmares.singularityChess.ui.dialog;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.R;
import com.petrifiednightmares.singularityChess.ui.GameDrawable;
import com.petrifiednightmares.singularityChess.ui.SUI;
import com.petrifiednightmares.singularityChess.utilities.SingularBitmapFactory;

public class PromotionTile extends GameDrawable
{

	private int _top, _left, _width, _height;
	private String _title;
	private RectF _rectf;
	private Rect _textBounds, _mHeight;
	private Paint _iconPaint;
	private Bitmap _icon;

	private static Paint _backgroundPaint;
	private static Bitmap _backgroundTexture;

	public PromotionTile(GameDrawingPanel gdp, String title, Bitmap icon, int top, int left,
			int width, int height)
	{
		super(gdp);
		this._icon = icon;
		this._top = top;
		this._left = left;
		this._width = width;
		this._title = title;
		this._height = height;
		this._rectf = new RectF(_left, SUI.HEIGHT + SUI.UNIT * 10, _left + _width, _top + _height);

		PromotionTile._backgroundTexture = SingularBitmapFactory.buildBitmap(gdp.getResources(),
				R.drawable.tile_wood);

		PromotionTile._backgroundPaint = new Paint();
		PromotionTile._backgroundPaint.setAntiAlias(true);
		PromotionTile._backgroundPaint.setShader(new BitmapShader(PromotionTile._backgroundTexture,
				Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));

		// this._backgroundPaint.

		this._iconPaint = new Paint();
		this._iconPaint.setAntiAlias(true);
		this._iconPaint.setColor(Color.WHITE);
		this._iconPaint.setTextSize(20);
		this._iconPaint.setAlpha(120);
		this._iconPaint.setShadowLayer(15, 0, 0, Color.argb(70, 255, 255, 255));

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
			c.drawRect(_rectf, PromotionTile._backgroundPaint);
			c.drawBitmap(_icon, _left + _width / 2 - _icon.getWidth() / 2, _rectf.top
					+ (int) (_height * 0.8) / 2 - _icon.getHeight() / 2, _iconPaint);

			c.drawText(_title, _left + _width / 2 - _textBounds.width() / 2, (int) (_rectf.top + _height
					* 0.8 + (_height * 0.2) / 2 + _mHeight.height() / 2), _iconPaint);
		}
	}

	@Override
	public boolean onClick(int x, int y)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void setTop(int i)
	{
		_rectf.top = i;
	}

	@Override
	public void redraw()
	{
		NEEDS_REDRAW = true;
	}

}