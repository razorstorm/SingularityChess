package com.petrifiednightmares.singularityChess.ui;

import android.graphics.Canvas;

public class BottomBar
{
	private static boolean NEEDS_REDRAW;
	ActionButton[] buttons;

	public BottomBar()
	{
		NEEDS_REDRAW=true;
		buttons = new ActionButton[1];
		buttons[0] = new ActionButton("Show Moves", SUI.HEIGHT - SUI.PADDING - SUI.UNIT * 10, 61,
				SUI.UNIT * 25, SUI.UNIT * 10);
	}

	public void onDraw(Canvas c)
	{
		if (NEEDS_REDRAW)
		{
			NEEDS_REDRAW = false;
			
			for(ActionButton b: buttons)
			{
				b.onDraw(c);
			}
		}
	}
}
