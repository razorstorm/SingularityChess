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
import com.petrifiednightmares.singularityChess.ui.GameUI;
import com.petrifiednightmares.singularityChess.ui.SUI;

public class InstructionsDialog extends HoverDialog
{
	TextView	_instructionText;
	RectF		_movesViewRect;

	Paint		_horizontalPaint, _verticalPaint, _dimWhite;

	public InstructionsDialog(GameDrawingPanel gdp, GameUI gui)
	{
		super(gdp, gui, "Instructions", (SUI.HEIGHT / 100) * 20, (SUI.WIDTH / 100) * 10, SUI.WIDTH - 2
				* ((SUI.WIDTH / 100) * 10), SUI.HEIGHT - 2 * (SUI.HEIGHT / 100) * 20);

		_instructionText = (TextView) (gdp.getLayoutResource(R.id.instructions_view));

		int viewHeight = (int) ((_height - _headerBottom) * 0.8);
		int viewWidth = (int) (_width * 0.8);
		int viewLeft = _left + (_width - viewWidth) / 2;
		int viewTop = _top + _headerBottom + ((_height - _headerBottom) - viewHeight) / 2;

		_instructionText.getLayoutParams().height = viewHeight;
		_instructionText.getLayoutParams().width = viewWidth;
		((RelativeLayout.LayoutParams) _instructionText.getLayoutParams()).leftMargin = viewLeft;
		((RelativeLayout.LayoutParams) _instructionText.getLayoutParams()).topMargin = viewTop;

		_instructionText.setPadding(20, 20, 20, 20);

		_movesViewRect = new RectF(viewLeft - _left, viewTop - _top, viewLeft - _left + viewWidth,
				viewTop - _top + viewHeight);

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

		_instructionText.setTextColor(Color.WHITE);
		_instructionText.setTextSize(15);

		_dimWhite = new Paint();
		_dimWhite.setAntiAlias(true);
		_dimWhite.setColor(Color.WHITE);
		_dimWhite.setAlpha(60);

		augmentBitmap();
	}

	private void augmentBitmap()
	{
		Canvas c = new Canvas(_background);

		c.drawRoundRect(_movesViewRect, (_movesViewRect.bottom - _movesViewRect.top) * 0.01f,
				(_movesViewRect.bottom - _movesViewRect.top) * 0.01f, _horizontalPaint);
		c.drawRoundRect(_movesViewRect, (_movesViewRect.bottom - _movesViewRect.top) * 0.01f,
				(_movesViewRect.bottom - _movesViewRect.top) * 0.01f, _verticalPaint);

		c.drawRect(_movesViewRect.left + (_movesViewRect.bottom - _movesViewRect.top) * 0.01f,
				_movesViewRect.bottom, _movesViewRect.right
						- (_movesViewRect.bottom - _movesViewRect.top) * 0.01f,
				_movesViewRect.bottom + 1, _dimWhite);
	}

	public void onDraw(Canvas c)
	{
		// only check, don't set
		super.onDraw(c);
	}

	@Override
	public synchronized void display()
	{
		super.display();
		_instructionText.setVisibility(View.VISIBLE);
	}

	@Override
	public synchronized void hide()
	{
		super.hide();
		_instructionText.setVisibility(View.INVISIBLE);
	}

}
