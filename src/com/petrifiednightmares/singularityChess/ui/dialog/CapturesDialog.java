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

public class CapturesDialog extends HoverDialog
{

	private RectF _whiteBox, _blackBox;

	private int _tileWidth, _tileHeight, _tileLeft, _tileTop, _gap;

	private Paint _horizontalPaint, _verticalWhitePaint, _verticalBlackPaint;
	private MoveLogger _ml;

	private TextView _whiteText, _blackText;

	public CapturesDialog(GameDrawingPanel gdp, GameUI gui, MoveLogger ml)
	{
		super(gdp, gui, "Captures", (SUI.HEIGHT / 100) * 25, (SUI.WIDTH / 100) * 10, SUI.WIDTH - 2
				* ((SUI.WIDTH / 100) * 10), SUI.HEIGHT - 2 * (SUI.HEIGHT / 100) * 25);

		_ml = ml;

		_tileWidth = (int) (_width * 0.75);
		int totalHeight = (int) ((_height - _headerBottom) * 0.8);

		_tileLeft = (_width - _tileWidth) / 2;

		_gap = (int) (_height * 0.05);
		_tileHeight = (int) ((totalHeight - _gap) / 2);

		int tileMargin = ((_height - (_headerBottom)) - totalHeight) / 2;
		_tileTop = _headerBottom + tileMargin;

		_whiteBox = new RectF(_tileLeft, _tileTop, _tileLeft + _tileWidth, _tileTop + _tileHeight);
		_blackBox = new RectF(_tileLeft, _tileTop + _tileHeight + _gap, _tileLeft + _tileWidth,
				_tileTop + _tileHeight + _gap + _tileHeight);

		this._horizontalPaint = new Paint();
		this._horizontalPaint.setAntiAlias(true);
		this._horizontalPaint.setShader(new LinearGradient(_tileLeft, _tileHeight / 2f, _tileLeft
				+ _tileWidth, _tileHeight / 2f, new int[] { Color.rgb(30, 21, 9),
				Color.rgb(62, 43, 18), Color.rgb(62, 43, 18), Color.rgb(30, 21, 9) }, new float[] {
				0f, 0.1f, 0.9f, 1f }, Shader.TileMode.MIRROR));
		this._horizontalPaint.setAlpha(150);

		this._verticalWhitePaint = new Paint();
		this._verticalWhitePaint.setAntiAlias(true);
		this._verticalWhitePaint.setShader(new LinearGradient(_tileLeft + _tileWidth / 2f,
				_tileTop, _tileLeft + _tileWidth / 2f, _tileTop + _tileHeight, new int[] {
						Color.rgb(30, 21, 9), Color.rgb(62, 43, 18), Color.rgb(62, 43, 18),
						Color.rgb(30, 21, 9), Color.WHITE }, new float[] { 0f, 0.1f, 0.9f,
						1f - 1f / _tileHeight, 1f }, Shader.TileMode.MIRROR));
		this._verticalWhitePaint.setAlpha(100);

		this._verticalBlackPaint = new Paint();
		this._verticalBlackPaint.setAntiAlias(true);
		this._verticalBlackPaint.setShader(new LinearGradient(_tileLeft + _tileWidth / 2f, _tileTop
				+ _tileHeight + _gap, _tileLeft + _tileWidth / 2f, _tileTop + _tileHeight + _gap
				+ _tileHeight, new int[] { Color.rgb(30, 21, 9), Color.rgb(62, 43, 18),
				Color.rgb(62, 43, 18), Color.rgb(30, 21, 9), Color.WHITE }, new float[] { 0f, 0.1f,
				0.9f, 1f - 1f / _tileHeight, 1f }, Shader.TileMode.MIRROR));
		this._verticalBlackPaint.setAlpha(100);

		augmentBitmap();

		setupTextViews();

	}

	private void setupTextViews()
	{
		_whiteText = (TextViewOutline) gdp.getLayoutResource(R.id.whiteCapturesTextView);
		_blackText = (TextViewOutline) gdp.getLayoutResource(R.id.blackCapturesTextView);

		((TextViewOutline) _whiteText).initTextViewOutline(Color.BLACK, Color.WHITE, 40, 2, true);
		((TextViewOutline) _blackText).initTextViewOutline(Color.BLACK, Color.WHITE, 40, 2, true);

		_whiteText.getLayoutParams().height = _tileHeight;
		_whiteText.getLayoutParams().width = _tileWidth;
		((RelativeLayout.LayoutParams) _whiteText.getLayoutParams()).leftMargin = _tileLeft + _left;
		((RelativeLayout.LayoutParams) _whiteText.getLayoutParams()).topMargin = _tileTop + _top;

		_blackText.getLayoutParams().height = _tileHeight;
		_blackText.getLayoutParams().width = _tileWidth;
		((RelativeLayout.LayoutParams) _blackText.getLayoutParams()).leftMargin = _tileLeft + _left;
		((RelativeLayout.LayoutParams) _blackText.getLayoutParams()).topMargin = _tileTop
				+ _tileHeight + _gap + _top;

		_whiteText.bringToFront();
		_blackText.bringToFront();

		_whiteText.setPadding(10, 10, 10, 10);
		_blackText.setPadding(10, 10, 10, 10);

	}

	private void augmentBitmap()
	{
		Canvas c = new Canvas(_background);

		c.drawRoundRect(_whiteBox, _tileWidth * 0.01f, _tileWidth * 0.01f, _horizontalPaint);
		c.drawRoundRect(_whiteBox, _tileWidth * 0.01f, _tileWidth * 0.01f, _verticalWhitePaint);
		c.drawRoundRect(_blackBox, _tileWidth * 0.01f, _tileWidth * 0.01f, _horizontalPaint);
		c.drawRoundRect(_blackBox, _tileWidth * 0.01f, _tileWidth * 0.01f, _verticalBlackPaint);

	}

	@Override
	public synchronized void display()
	{
		super.display();

		_whiteText.setVisibility(View.VISIBLE);
		_blackText.setVisibility(View.VISIBLE);
		_whiteText.setText(_ml.generateCapturedWhitePiecesString());
		_blackText.setText(_ml.generateCapturedBlackPiecesString());
	}

	@Override
	public synchronized void hide()
	{
		super.hide();
		_whiteText.setVisibility(View.INVISIBLE);
		_blackText.setVisibility(View.INVISIBLE);
	}

	public void setMoveLogger(MoveLogger ml)
	{
		this._ml = ml;
	}

}
