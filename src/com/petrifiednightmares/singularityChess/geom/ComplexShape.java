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

	// bad solution :(
	/*
	 * Problem: Need to find center of the square so I can know where to draw
	 * the piece 
	 * 
	 * Solution 1: Use a very hardcoded solution that resides on the
	 * assumption that there are 2 circles and a rectangle to every ComplexShape
	 * class. Use this to find an inscribed rectangle, and then find the center.
	 **** Downsides: Not very abstract, not extensible, breaks abstraction. Also,
	 * has to do a lot of if and elses because orientation invariant is not
	 * conserved 
	 * 
	 * Solution 2: Do some type of pixel level geometry to find the
	 * inscribed rectangle.
	 **** Downsides: Don't know how, probably slow
	 *
	 * Solution 3: Do some sort of center of mass (or center of volume) calculation to find the center
	 **** Downsides: Don't know how, probably slow
	 *
	 *Solution 4: Use getClipBounds to get the circumscribed rectangle and then find center
	 **** Downsides: Inaccurate, will pick a very bad center for oddly shaped Squares. 
	 */
//	private Rect getInnerClipBounds(Rect outterBounds)
//	{
//		int left = outterBounds.left;
//		int right = outterBounds.right;
//		
//		int topLeft = 
//		return null;
//	}

	public int getX()
	{
		return _x;
	}

	public int getY()
	{
		return _y;
	}

}
