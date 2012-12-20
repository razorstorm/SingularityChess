package com.petrifiednightmares.singularityChess.ui;

import android.graphics.Canvas;
import android.view.HapticFeedbackConstants;

import com.petrifiednightmares.singularityChess.GameActivity;
import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.utilities.SingularBitmapFactory;

public class BottomBar extends GameDrawable
{
	ActionButton[]	buttons;
	private int		_barWidth, _space, _buttonWidth, _top, _height, _thinButtonWidth;

	private GameUI	_gui;

	public BottomBar(GameUI gui, GameDrawingPanel gdp)
	{
		super(gdp);
		this._gui = gui;

		_barWidth = SUI.WIDTH - SUI.PADDING * 2;
		_buttonWidth = SUI.UNIT * 25;
		_thinButtonWidth = SUI.UNIT * 8;
		_height = SUI.UNIT * 10;
		_top = SUI.HEIGHT - SUI.PADDING - _height;

		_space = (_barWidth - 3 * _buttonWidth - _thinButtonWidth) / 6;

		buttons = new ActionButton[4];
		buttons[0] = new ActionButton("Show Moves", _top, SUI.PADDING, _buttonWidth, _height);

		buttons[1] = new ActionButton("Show Captures", _top, SUI.PADDING + _space * 2
				+ _buttonWidth, _buttonWidth, _height);

		buttons[2] = new ActionButton("Surrender", _top, SUI.PADDING + _space * 4 + _buttonWidth
				* 2, _buttonWidth, _height);

		buttons[3] = new ActionButton(SingularBitmapFactory.buildScaledBitmap(gdp.getResources(),
				android.R.drawable.ic_menu_preferences, (int) (_thinButtonWidth * 0.6),
				(int) (_height * 0.5)), _top, SUI.PADDING + _space * 6 + _buttonWidth * 3,
				_thinButtonWidth, _height);

	}

	// returns whether a prompt is open
	public boolean onClick(int x, int y)
	{
		for (int i = 0; i < 4; i++)
		{
			ActionButton b = buttons[i];
			if (b.onClick(x, y))
			{
				switch (i)
				{
					case 0:
						_gui.movesDialog.display();
						_gui.PROMPT_WAITING = true;
						_gui.PROMPT = _gui.movesDialog;
						break;
					case 1:
						_gui.capturesDialog.display();
						_gui.PROMPT_WAITING = true;
						_gui.PROMPT = _gui.capturesDialog;
						break;
					case 2:
						_gui.surrender();
						break;
					case 3:
						// expand menu
						((GameActivity) gdp.getContext()).openOptionsMenu();
						break;
				}
				gdp.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
			}
		}
		return false;
	}

	public void onDraw(Canvas c)
	{
		for (ActionButton b : buttons)
		{
			b.onDraw(c);
		}
	}
}
