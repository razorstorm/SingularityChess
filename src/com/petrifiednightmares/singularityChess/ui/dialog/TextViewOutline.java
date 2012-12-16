package com.petrifiednightmares.singularityChess.ui.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
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

	// modify initTextViewOutline to setup the outline style
	public void initTextViewOutline(int textColor, int outlineColor, int textSize, int strokeWidth)
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

	}

	private void drawMultilineText(String str, float x, float y, Paint paint, Canvas canvas)
	{
		int lineHeight = 0;
		int yoffset = 0;
		String[] lines = str.split("\n");

		Rect mBounds = new Rect();
		// set height of each line (height of text + 20%)
		paint.getTextBounds("Ig", 0, 2, mBounds);
		lineHeight = (int) ((float) mBounds.height() * 1.2);
		// draw each line
		for (int i = 0; i < lines.length; ++i)
		{
			canvas.drawText(lines[i], x, y + yoffset, paint);
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
		System.out.println(mTextPaint.getTextSize());
		System.out.println(mTextPaint.ascent());
		System.out.println(getPaddingTop());
		// super.onDraw(canvas);
		
		drawMultilineText(getText().toString(), getPaddingLeft(),
				getPaddingTop() - mTextPaintOutline.ascent(), mTextPaintOutline,canvas);
		
		drawMultilineText(getText().toString(), getPaddingLeft(),
				getPaddingTop() - mTextPaintOutline.ascent(), mTextPaint,canvas);
		
//		canvas.drawText(getText().toString(), getPaddingLeft(),
//				getPaddingTop() - mTextPaintOutline.ascent(), mTextPaintOutline);
//		canvas.drawText(getText().toString(), getPaddingLeft(),
//				getPaddingTop() - mTextPaint.ascent(), mTextPaint);
	}
}