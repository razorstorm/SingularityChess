package com.petrifiednightmares.singularityChess.ui.dialog;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.R;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.ui.SUI;
import com.petrifiednightmares.singularityChess.utilities.SingularBitmapFactory;

public class PromotionDialog extends HoverDialog
{
	Game _game;
	PromotionTile queenTile, knightTile;
	private Bitmap _bgTexture;

	public PromotionDialog(GameDrawingPanel gdp, Game game)
	{
		super(gdp, "Promotion", (SUI.HEIGHT / 100) * 20, (SUI.WIDTH / 100) * 10, SUI.WIDTH - 2
				* ((SUI.WIDTH / 100) * 10), SUI.HEIGHT - 2 * (SUI.HEIGHT / 100) * 20);

		this._game = game;

		int tileWidth = (int) (_width * 0.4);
		int tileTopMargin = (_height - tileWidth) / 2;
		int tileLeftMargin = (_width / 2 - tileWidth) / 2;
		int _tileTop = _top + tileTopMargin;

		_backgroundPaint = new Paint();
		_backgroundPaint.setAntiAlias(true);
		_backgroundPaint.setColor(Color.WHITE);
		_backgroundPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

		_bgTexture = SingularBitmapFactory.buildScaledBitmap(gdp.getResources(),
				R.drawable.wood_board, _width, _height);

		Bitmap queenBitmap = SingularBitmapFactory.buildScaledBitmap(gdp.getResources(),
				game.isWhiteTurn() ? R.drawable.queen : R.drawable.black_queen,
				(int) (tileWidth * 0.7), (int) (tileWidth * 0.64));

		Bitmap knightBitmap = SingularBitmapFactory.buildScaledBitmap(gdp.getResources(),
				game.isWhiteTurn() ? R.drawable.knight : R.drawable.black_knight,
				(int) (tileWidth * 0.7), (int) (tileWidth * 0.64));

		queenTile = new PromotionTile(gdp, "Queen", queenBitmap, _tileTop, _left + tileLeftMargin,
				tileWidth, tileWidth);
		knightTile = new PromotionTile(gdp, "Knight", knightBitmap, _tileTop, _left + _width / 2
				+ tileLeftMargin, tileWidth, tileWidth);
	}

	public synchronized void display()
	{
		super.display();
		queenTile.show();
		queenTile.redraw();

		knightTile.show();
		knightTile.redraw();
		redraw();
	}

	public synchronized void hide()
	{
		super.hide();
		queenTile.hide();
		queenTile.redraw();

		knightTile.hide();
		knightTile.redraw();
	}

	public void onDraw(Canvas c)
	{
		if (NEEDS_REDRAW)
		{
			NEEDS_REDRAW = false;
			if (_shown)
			{
				c.drawRoundRect(_rectf, _height * 0.01f, _height * 0.01f, _backgroundPaint);

				c.drawBitmap(_bgTexture, _rectf.left, _rectf.top, _backgroundPaint);
				// c.drawRoundRect(_rectf, _height * 0.01f, _height * 0.01f,
				// SUI.gameLightingPaint);

				queenTile.onDraw(c);
				knightTile.onDraw(c);

				c.drawText(_title, _rectf.left + SUI.UNIT * 5, _rectf.top + SUI.UNIT * 8,
						_textPaint);

				c.drawRect(_rectf.left, _rectf.top + _mHeight.height() + SUI.UNIT * 10, _left
						+ _width, _rectf.top + _mHeight.height() + SUI.UNIT * 10 + 1,
						_backgroundPaint);
			}
		}
	}

	public boolean onClick(int x, int y)
	{
		if (super.onClick(x, y))
		{

			return true;
		}
		return false;
	}
}
