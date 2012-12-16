package com.petrifiednightmares.singularityChess.ui.dialog;

import java.util.Arrays;
import java.util.LinkedList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewOutline extends TextView
{

	public TextViewOutline(Context context, AttributeSet aSet)
	{
		super(context, aSet);
	}

	private Paint mTextPaint;
	private Paint mTextPaintOutline; // add another paint attribute for your
										// outline
	private boolean _wrap, _interleave;
	private Paint _interleavePaint;
	private int _interleaveCount;

	// modify initTextViewOutline to setup the outline style
	public void initTextViewOutline(int textColor, int outlineColor, int textSize, int strokeWidth,
			boolean wrap)
	{
		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setTextSize(textSize);
		mTextPaint.setColor(textColor);
		mTextPaint.setStyle(Paint.Style.FILL);

		mTextPaintOutline = new Paint();
		mTextPaintOutline.setAntiAlias(true);
		mTextPaintOutline.setTextSize(textSize);
		mTextPaintOutline.setColor(outlineColor);
		mTextPaintOutline.setStyle(Paint.Style.STROKE);
		mTextPaintOutline.setStrokeWidth(strokeWidth);

		_wrap = wrap;
	}

	public void setBold(boolean boldness)
	{
		mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
		mTextPaintOutline.setTypeface(Typeface.DEFAULT_BOLD);
	}

	public void setInterleave(boolean interleave, int color)
	{
		_interleave = interleave;
		_interleaveCount = 0;
		_interleavePaint = new Paint();
		_interleavePaint.setAntiAlias(true);
		_interleavePaint.setTextSize(mTextPaint.getTextSize());
		_interleavePaint.setColor(color);
		_interleavePaint.setStyle(Paint.Style.FILL);
	}

	private void drawMultilineText(String str, float x, float y, Paint paint, Canvas canvas,
			boolean isOutline)
	{
		_interleaveCount=0;
		int lineHeight = 0;
		int yoffset = 0;
		LinkedList<String> lines = new LinkedList<String>(Arrays.asList(str.split("\n")));

		Rect mBounds = new Rect();
		// set height of each line (height of text + 20%)
		paint.getTextBounds(str, 0, 2, mBounds);
		lineHeight = (int) ((float) mBounds.height() * 1.5);

		// draw each line
		for (int i = 0; i < lines.size(); ++i)
		{
			if (_wrap)
			{
				StaticLayout layout = new StaticLayout(lines.get(i), new TextPaint(paint),
						getWidth(), Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false);

				canvas.save();
				canvas.translate(x, y + yoffset); // position the text
				layout.draw(canvas);
				canvas.restore();
			}
			else
			{
				if (_interleave)
				{
					if ((_interleaveCount % 2 == 0))
					{
						if (!isOutline)
						{
							canvas.drawText(lines.get(i), x, y + yoffset, _interleavePaint);
						}
					}
					else
					{
						canvas.drawText(lines.get(i), x, y + yoffset, paint);
					}
				}
				else
				{
					canvas.drawText(lines.get(i), x, y + yoffset, paint);
				}
			}

			if (_interleave)
				_interleaveCount++;

			yoffset = yoffset + lineHeight;
		}
	
	}

	// make sure to update other methods you've overridden to handle your new
	// paint object
	// and finally draw the text, mAscent refers to a member attribute which had
	// a value assigned to it in the measureHeight and Width methods

	@Override
	protected void onDraw(Canvas canvas)
	{
		float top = getPaddingTop();
		if (!_wrap)
			top -= mTextPaint.ascent();

		drawMultilineText(getText().toString(), getPaddingLeft(), top, mTextPaintOutline, canvas,
				true);

		drawMultilineText(getText().toString(), getPaddingLeft(), top, mTextPaint, canvas, false);
	}
}