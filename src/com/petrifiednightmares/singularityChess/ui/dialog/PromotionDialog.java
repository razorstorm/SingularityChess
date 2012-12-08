package com.petrifiednightmares.singularityChess.ui.dialog;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.R;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.ui.SUI;
import com.petrifiednightmares.singularityChess.utilities.SingularBitmapFactory;

public class PromotionDialog extends HoverDialog
{
	Game _game;
	PromotionTile queenTile, knightTile;

	private int _tileTop;

	public PromotionDialog(GameDrawingPanel gdp, Game game)
	{
		super(gdp, "Promotion", (SUI.HEIGHT / 100) * 20, (SUI.WIDTH / 100) * 10, SUI.WIDTH - 2
				* ((SUI.WIDTH / 100) * 10), SUI.HEIGHT - 2 * (SUI.HEIGHT / 100) * 20);

		this._game = game;

		int tileWidth = (int) (_width * 0.4);
		int tileTopMargin = (_height - tileWidth) / 2;
		int tileLeftMargin = (_width / 2 - tileWidth) / 2;

		_tileTop = _top + tileTopMargin;

		Bitmap queenBitmap = SingularBitmapFactory.buildScaledBitmap(gdp.getResources(),
				game.isWhiteTurn() ? R.drawable.queen : R.drawable.black_queen,
				(int) (tileWidth * 0.7), (int) (tileWidth * 0.64));

		Bitmap knightBitmap = SingularBitmapFactory.buildScaledBitmap(gdp.getResources(),
				game.isWhiteTurn() ? R.drawable.knight : R.drawable.black_knight,
				(int) (tileWidth * 0.7), (int) (tileWidth * 0.64));

		queenTile = new PromotionTile(gdp, "Queen", queenBitmap, _tileTop, _left
				+ tileLeftMargin, tileWidth, tileWidth);
		knightTile = new PromotionTile(gdp, "Knight", knightBitmap, _tileTop,
				_left + _width / 2 + tileLeftMargin, tileWidth, tileWidth);
	}

	public synchronized void display()
	{
		super.display();
		queenTile.setTop(_tileTop);
		queenTile.redraw();

		knightTile.setTop(_tileTop);
		knightTile.redraw();
		redraw();
	}

	public synchronized void hide()
	{
		super.hide();
		_rectf.top = SUI.HEIGHT + SUI.UNIT * 10;
		queenTile.setTop(SUI.HEIGHT + SUI.UNIT * 10);
		queenTile.redraw();

		knightTile.setTop(SUI.HEIGHT + SUI.UNIT * 10);
		knightTile.redraw();
	}

	public void onDraw(Canvas c)
	{
		super.onDraw(c);
		queenTile.onDraw(c);
		knightTile.onDraw(c);
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
