package com.petrifiednightmares.singularityChess.ui;

import android.graphics.Canvas;
import android.view.HapticFeedbackConstants;

import com.petrifiednightmares.singularityChess.GameActivity;
import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.logic.Game;

public class BottomBar
{
	public static boolean NEEDS_REDRAW;
	ActionButton[] buttons;
	private int _barWidth, _space, _buttonWidth, _top, _height, _thinButtonWidth;

	private GameDrawingPanel _gdp;

	public BottomBar(GameDrawingPanel gdp)
	{
		this._gdp = gdp;

		_barWidth = SUI.WIDTH - SUI.PADDING * 2;
		_buttonWidth = SUI.UNIT * 25;
		_thinButtonWidth = SUI.UNIT * 8;
		_height = SUI.UNIT * 10;
		_top = SUI.HEIGHT - SUI.PADDING - _height;

		_space = (_barWidth - 3 * _buttonWidth - _thinButtonWidth) / 6;

		NEEDS_REDRAW = true;
		buttons = new ActionButton[4];
		buttons[0] = new ActionButton("Show Moves", _top, SUI.PADDING, _buttonWidth, _height);

		buttons[1] = new ActionButton("Show Captures", _top, SUI.PADDING + _space * 2
				+ _buttonWidth, _buttonWidth, _height);

		buttons[2] = new ActionButton("Surrender", _top, SUI.PADDING + _space * 4 + _buttonWidth
				* 2, _buttonWidth, _height);

		buttons[3] = new ActionButton("⋮ ", _top, SUI.PADDING + _space * 6 + _buttonWidth * 3,
				_thinButtonWidth, _height); // TODO find icon from that thing we
											// used for rails to find a
											// paragraph icon

	}

	public void onClick(int x, int y)
	{
		for (int i = 0; i < 4; i++)
		{
			ActionButton b = buttons[i];
			if (b.onClick(x, y))
			{
				switch (i)
				{
				case 0:
					_gdp.game.movesDialog.display();
					Game.PROMPT_WAITING = true;
					Game.PROMPT = _gdp.game.movesDialog;
					break;
				case 3:
					// expand menu
					((GameActivity) _gdp.getContext()).openOptionsMenu();
					_gdp.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
					break;
				}
			}
		}
	}

	public void onDraw(Canvas c)
	{
		if (NEEDS_REDRAW)
		{
			NEEDS_REDRAW = false;

			for (ActionButton b : buttons)
			{
				b.onDraw(c);
			}
		}
	}
}
