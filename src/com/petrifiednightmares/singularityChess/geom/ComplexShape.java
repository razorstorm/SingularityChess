package com.petrifiednightmares.singularityChess.geom;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Rect;

public class ComplexShape
{
	// shapes that define where inside is. intersected
	ArrayList<AbstractShape> insideShape;

	// These shapes are regions where the complexshape does NOT lie.
	ArrayList<AbstractShape> outsideShape;

	private int _x, _y;

	public ComplexShape()
	{
		insideShape = new ArrayList<AbstractShape>();
		outsideShape = new ArrayList<AbstractShape>();
	}

	public void addInsideShape(AbstractShape s)
	{
		insideShape.add(s);
	}

	public void addOutsideShape(AbstractShape s)
	{
		outsideShape.add(s);
	}

	public boolean containsPoint(int x, int y)
	{
		for (AbstractShape s : insideShape)
		{
			if (!s.containsPoint(x, y))
			{
				return false;
			}
		}
		for (AbstractShape s : outsideShape)
		{
			if (s.containsPoint(x, y))
			{
				return false;
			}
		}
		return true;
	}

	public void clip(Canvas c)
	{
		for (AbstractShape s : insideShape)
		{
			s.insideClip(c);
		}
		for (AbstractShape s : outsideShape)
		{
			s.outsideClip(c);
		}

		Rect bounds = c.getClipBounds();
		_x = bounds.centerX();
		_y = bounds.centerY();
	}

	public int getX()
	{
		return _x;
	}

	public int getY()
	{
		return _y;
	}

}
