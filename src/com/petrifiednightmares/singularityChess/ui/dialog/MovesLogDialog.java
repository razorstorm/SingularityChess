package com.petrifiednightmares.singularityChess.ui.dialog;

import android.graphics.Canvas;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.ui.GameUI;
import com.petrifiednightmares.singularityChess.ui.SUI;

public class MovesLogDialog extends HoverDialog
{
	public MovesLogDialog(GameDrawingPanel gdp, GameUI gui)
	{
		super(gdp, gui, "Moves", (SUI.HEIGHT / 100) * 20, (SUI.WIDTH / 100) * 10, SUI.WIDTH - 2
				* ((SUI.WIDTH / 100) * 10), SUI.HEIGHT - 2 * (SUI.HEIGHT / 100) * 20);
	}

	public void onDraw(Canvas c)
	{
		super.onDraw(c);
	}
}
