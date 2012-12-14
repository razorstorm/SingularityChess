package com.petrifiednightmares.singularityChess.ui.dialog;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.R;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;
import com.petrifiednightmares.singularityChess.ui.GameUI;
import com.petrifiednightmares.singularityChess.ui.SUI;
import com.petrifiednightmares.singularityChess.utilities.SingularBitmapFactory;

public class PromotionDialog extends HoverDialog
{
	Game _game;
	PromotionTile queenTile, knightTile;

	public PromotionDialog(GameDrawingPanel gdp, Game game, GameUI gui)
	{
		super(gdp, gui, "Promotion", (SUI.HEIGHT / 100) * 30, (SUI.WIDTH / 100) * 10, SUI.WIDTH - 2
				* ((SUI.WIDTH / 100) * 10), SUI.HEIGHT - 2 * (SUI.HEIGHT / 100) * 30);

		this._game = game;

		int tileWidth = (int) (_width * 0.35);
		int tileTopMargin = (_height - tileWidth) / 2;
		int tileMargin = (_width - tileWidth * 2) / 6;
		int tileLeftMargin = tileMargin * 2;
		int _tileTop = _top + tileTopMargin;

		Bitmap whiteQueenBitmap = SingularBitmapFactory.buildScaledBitmap(gdp.getResources(),
				R.drawable.queen, (int) (tileWidth * 0.7), (int) (tileWidth * 0.64));

		Bitmap blackQueenBitmap = SingularBitmapFactory.buildScaledBitmap(gdp.getResources(),
				R.drawable.black_queen, (int) (tileWidth * 0.7), (int) (tileWidth * 0.64));

		Bitmap whiteKnightBitmap = SingularBitmapFactory.buildScaledBitmap(gdp.getResources(),
				R.drawable.knight, (int) (tileWidth * 0.7), (int) (tileWidth * 0.64));

		Bitmap blackKnightBitmap = SingularBitmapFactory.buildScaledBitmap(gdp.getResources(),
				R.drawable.black_knight, (int) (tileWidth * 0.7), (int) (tileWidth * 0.64));

		queenTile = new PromotionTile(gdp, "Queen", whiteQueenBitmap, blackQueenBitmap, _tileTop,
				_left + tileLeftMargin, tileWidth, tileWidth);
		knightTile = new PromotionTile(gdp, "Knight", whiteKnightBitmap, blackKnightBitmap,
				_tileTop, _left + _width / 2 + tileMargin, tileWidth, tileWidth);
	}

	public synchronized void display(boolean isWhite)
	{
		super.display();
		queenTile.show(isWhite);
		queenTile.redraw();

		knightTile.show(isWhite);
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
		if (_shown)
		{
			super.onDraw(c);
			queenTile.onDraw(c);
			knightTile.onDraw(c);
		}
	}

	public boolean onClick(int x, int y)
	{
		if (queenTile.onClick(x, y))
		{
			hide();
			_gui.closePrompt();
			_gui.promote(AbstractPiece.PieceType.Queen);
			return true;
		} else if (knightTile.onClick(x, y))
		{
			hide();
			_gui.closePrompt();
			_gui.promote(AbstractPiece.PieceType.Knight);
		}
		return false;
	}
}
