package com.petrifiednightmares.singularityChess.ui.dialog;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.logging.MoveLogger;
import com.petrifiednightmares.singularityChess.ui.GameUI;
import com.petrifiednightmares.singularityChess.ui.SUI;

public class CapturesDialog extends HoverDialog
{

	RectF _whiteBox, _blackBox;

	int tileWidth, tileHeight;

	Paint _horizontalPaint, _verticalWhitePaint, _verticalBlackPaint, _dimWhite;

	public CapturesDialog(GameDrawingPanel gdp, GameUI gui, MoveLogger ml)
	{
		super(gdp, gui, "Promotion", (SUI.HEIGHT / 100) * 25, (SUI.WIDTH / 100) * 10, SUI.WIDTH - 2
				* ((SUI.WIDTH / 100) * 10), SUI.HEIGHT - 2 * (SUI.HEIGHT / 100) * 25);

		tileWidth = (int) (_width * 0.75);
		int totalHeight = (int) ((_height - _headerBottom) * 0.8);

		int tileLeft = (_width - tileWidth) / 2;

		int gap = (int) (_height * 0.05);
		tileHeight = (int) ((totalHeight - gap) / 2);

		int tileMargin = ((_height - (_headerBottom)) - totalHeight) / 2;
		int tileTop = _headerBottom + tileMargin;

		_whiteBox = new RectF(tileLeft, tileTop, tileLeft + tileWidth, tileTop + tileHeight);
		_blackBox = new RectF(tileLeft, tileTop + tileHeight + gap, tileLeft + tileWidth, tileTop
				+ tileHeight + gap + tileHeight);

		this._horizontalPaint = new Paint();
		this._horizontalPaint.setAntiAlias(true);
		this._horizontalPaint.setShader(new LinearGradient(tileLeft, tileHeight / 2f, tileLeft
				+ tileWidth, tileHeight / 2f, new int[] { Color.rgb(30, 21, 9),
				Color.rgb(62, 43, 18), Color.rgb(62, 43, 18), Color.rgb(30, 21, 9) }, new float[] {
				0f, 0.1f, 0.9f, 1f }, Shader.TileMode.MIRROR));
		this._horizontalPaint.setAlpha(150);

		this._verticalWhitePaint = new Paint();
		this._verticalWhitePaint.setAntiAlias(true);
		this._verticalWhitePaint.setShader(new LinearGradient(tileLeft + tileWidth / 2f, tileTop,
				tileLeft + tileWidth / 2f, tileTop + tileHeight, new int[] { Color.rgb(30, 21, 9),
						Color.rgb(62, 43, 18), Color.rgb(62, 43, 18), Color.rgb(30, 21, 9),
						Color.WHITE }, new float[] { 0f, 0.1f, 0.9f, 1f - 1f / tileHeight, 1f },
				Shader.TileMode.MIRROR));
		this._verticalWhitePaint.setAlpha(100);

		this._verticalBlackPaint = new Paint();
		this._verticalBlackPaint.setAntiAlias(true);
		this._verticalBlackPaint.setShader(new LinearGradient(tileLeft + tileWidth / 2f, tileTop
				+ tileHeight + gap, tileLeft + tileWidth / 2f, tileTop + tileHeight + gap
				+ tileHeight, new int[] { Color.rgb(30, 21, 9), Color.rgb(62, 43, 18),
				Color.rgb(62, 43, 18), Color.rgb(30, 21, 9), Color.WHITE }, new float[] { 0f, 0.1f,
				0.9f, 1f - 1f / tileHeight, 1f }, Shader.TileMode.MIRROR));
		this._verticalBlackPaint.setAlpha(100);

		// _text.setTextSize(15);
		// _text.setTextColor(Color.WHITE);

		augmentBitmap();
	}

	public void augmentBitmap()
	{
		Canvas c = new Canvas(_background);

		c.drawRoundRect(_whiteBox, tileWidth * 0.01f, tileWidth * 0.01f, _horizontalPaint);
		c.drawRoundRect(_whiteBox, tileWidth * 0.01f, tileWidth * 0.01f, _verticalWhitePaint);
		c.drawRoundRect(_blackBox, tileWidth * 0.01f, tileWidth * 0.01f, _horizontalPaint);
		c.drawRoundRect(_blackBox, tileWidth * 0.01f, tileWidth * 0.01f, _verticalBlackPaint);

	}

	public void onDraw(Canvas c)
	{
		super.onDraw(c);
	}

}
