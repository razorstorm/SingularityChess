package com.petrifiednightmares.singularityChess.ui.dialog;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.R;
import com.petrifiednightmares.singularityChess.logging.MoveLogger;
import com.petrifiednightmares.singularityChess.ui.GameUI;
import com.petrifiednightmares.singularityChess.ui.SUI;

public class InstructionsDialog extends HoverDialog
{
	TextView _instructionsView;
	RectF _movesViewRect;

	Paint _horizontalPaint, _verticalPaint, _dimWhite;

	public InstructionsDialog(GameDrawingPanel gdp, GameUI gui)
	{
		super(gdp, gui, "Moves", (SUI.HEIGHT / 100) * 20, (SUI.WIDTH / 100) * 10, SUI.WIDTH - 2
				* ((SUI.WIDTH / 100) * 10), SUI.HEIGHT - 2 * (SUI.HEIGHT / 100) * 20);

		_instructionsView = (TextView) (gdp.getLayoutResource(R.id.instructions_view));

		int viewHeight = (int) ((_height - _headerBottom) * 0.8);
		int viewWidth = (int) (_width * 0.8);
		int viewLeft = _left + (_width - viewWidth) / 2;
		int viewTop = _top + _headerBottom + ((_height - _headerBottom) - viewHeight) / 2;

		_instructionsView.getLayoutParams().height = viewHeight;
		_instructionsView.getLayoutParams().width = viewWidth;
		((RelativeLayout.LayoutParams) _instructionsView.getLayoutParams()).leftMargin = viewLeft;
		((RelativeLayout.LayoutParams) _instructionsView.getLayoutParams()).topMargin = viewTop;


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

		
		_instructionsView.setTextColor(Color.WHITE);
		_instructionsView.setTextSize(15);

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
		_instructionsView.setVisibility(View.VISIBLE);
	}

	@Override
	public synchronized void hide()
	{
		super.hide();
		_instructionsView.setVisibility(View.INVISIBLE);
	}

	public void setMoveLogger(MoveLogger ml)
	{
	}
}
