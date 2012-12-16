package com.petrifiednightmares.singularityChess.ui.dialog;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.logging.MoveLogger;
import com.petrifiednightmares.singularityChess.ui.GameUI;
import com.petrifiednightmares.singularityChess.ui.SUI;

public class MovesLogDialog extends HoverDialog
{
	ScrollView _movesView;
	MoveLogger _ml;
	TextViewOutline _text;
	RectF _movesViewRect;

	Paint _horizontalPaint, _verticalPaint, _dimWhite;

	public MovesLogDialog(GameDrawingPanel gdp, GameUI gui, ScrollView movesView, MoveLogger ml)
	{
		super(gdp, gui, "Moves", (SUI.HEIGHT / 100) * 20, (SUI.WIDTH / 100) * 10, SUI.WIDTH - 2
				* ((SUI.WIDTH / 100) * 10), SUI.HEIGHT - 2 * (SUI.HEIGHT / 100) * 20);

		this._ml = ml;

		this._movesView = movesView;
		_movesView.bringToFront();
		_text = (TextViewOutline) (_movesView.getChildAt(0));

		int viewHeight = (int) ((_height - _headerBottom) * 0.8);
		int viewWidth = (int) (_width * 0.8);
		int viewLeft = _left + (_width - viewWidth) / 2;
		int viewTop = _top + _headerBottom + ((_height - _headerBottom) - viewHeight) / 2;

		_movesView.getLayoutParams().height = viewHeight;
		_movesView.getLayoutParams().width = viewWidth;
		((RelativeLayout.LayoutParams) movesView.getLayoutParams()).leftMargin = viewLeft;
		((RelativeLayout.LayoutParams) movesView.getLayoutParams()).topMargin = viewTop;

		((FrameLayout.LayoutParams) _text.getLayoutParams()).topMargin = (int) (viewHeight * 0.05);
		((FrameLayout.LayoutParams) _text.getLayoutParams()).leftMargin = (int) (viewWidth * 0.08);

		_movesViewRect = new RectF(viewLeft, viewTop, viewLeft + viewWidth, viewTop + viewHeight);

		this._horizontalPaint = new Paint();
		this._horizontalPaint.setAntiAlias(true);
		this._horizontalPaint.setShader(new LinearGradient(viewLeft, viewTop + viewHeight / 2f,
				viewLeft + viewWidth, viewTop + viewHeight / 2f, new int[] { Color.rgb(30, 21, 9),
						Color.rgb(62, 43, 18), Color.rgb(62, 43, 18), Color.rgb(30, 21, 9) },
				new float[] { 0f, 0.1f, 0.9f, 1f }, Shader.TileMode.MIRROR));
		this._horizontalPaint.setAlpha(150);

		this._verticalPaint = new Paint();
		this._verticalPaint.setAntiAlias(true);
		this._verticalPaint.setShader(new LinearGradient(viewLeft + viewWidth / 2f, viewTop,
				viewLeft + viewWidth / 2f, viewTop + viewHeight, new int[] { Color.rgb(30, 21, 9),
						Color.rgb(62, 43, 18), Color.rgb(62, 43, 18), Color.rgb(30, 21, 9),
						Color.WHITE }, new float[] { 0f, 0.1f, 0.9f, 1f - 2f / viewHeight, 1f },
				Shader.TileMode.MIRROR));
		this._verticalPaint.setAlpha(100);

		_text.initTextViewOutline(Color.BLACK, Color.WHITE, 30, 1, false);
		_text.setBold(true);
		_text.setInterleave(true, Color.WHITE);

		_dimWhite = new Paint();
		_dimWhite.setAntiAlias(true);
		_dimWhite.setColor(Color.WHITE);
		_dimWhite.setAlpha(60);
	}

	public void onDraw(Canvas c)
	{
		// only check, don't set
		super.onDraw(c);
		if (_shown)
		{
			c.drawRoundRect(_movesViewRect, (_movesViewRect.bottom - _movesViewRect.top) * 0.01f,
					(_movesViewRect.bottom - _movesViewRect.top) * 0.01f, _horizontalPaint);
			c.drawRoundRect(_movesViewRect, (_movesViewRect.bottom - _movesViewRect.top) * 0.01f,
					(_movesViewRect.bottom - _movesViewRect.top) * 0.01f, _verticalPaint);

			c.drawRect(_movesViewRect.left + (_movesViewRect.bottom - _movesViewRect.top) * 0.01f,
					_movesViewRect.bottom, _movesViewRect.right
							- (_movesViewRect.bottom - _movesViewRect.top) * 0.01f,
					_movesViewRect.bottom + 1, _dimWhite);
		}
	}

	@Override
	public synchronized void display()
	{
		super.display();
		_movesView.setVisibility(View.VISIBLE);
		_text.setText(_ml.returnLineSeparatedMoves());
	}

	@Override
	public synchronized void hide()
	{
		super.hide();
		_movesView.setVisibility(View.INVISIBLE);
	}

	public void setMoveLogger(MoveLogger ml)
	{
		this._ml = ml;
	}
}
