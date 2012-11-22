package com.petrifiednightmares.singularityChess.ui;

import android.graphics.Canvas;

public class BottomBar
{
	private static boolean NEEDS_REDRAW;
	ActionButton[] buttons;
	private int _barWidth, _space,_buttonWidth;
	
	public BottomBar()
	{
		_barWidth = SUI.WIDTH - SUI.PADDING*2;
		_space = _barWidth/3;
		_buttonWidth = SUI.UNIT * 25;
		
		NEEDS_REDRAW=true;
		buttons = new ActionButton[3];
		buttons[0] = new ActionButton("Show Moves", SUI.HEIGHT - SUI.PADDING - SUI.UNIT * 10, SUI.PADDING+_space*0 + (_space-_buttonWidth)/2,
				_buttonWidth, SUI.UNIT * 10);
		
		buttons[1] = new ActionButton("Show Captures", SUI.HEIGHT - SUI.PADDING - SUI.UNIT * 10, SUI.PADDING+_space*1 + (_space-_buttonWidth)/2,
				_buttonWidth, SUI.UNIT * 10);
		
		buttons[2] = new ActionButton("Surrender", SUI.HEIGHT - SUI.PADDING - SUI.UNIT * 10, SUI.PADDING+_space*2 + (_space-_buttonWidth)/2,
				_buttonWidth, SUI.UNIT * 10);
		
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
