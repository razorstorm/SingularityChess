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
		double x, y, r;
		x = y = r = 0;
		for (AbstractShape s : insideShape)
		{
			s.insideClip(c);
			if (Circle.class.isInstance(s))
			{
				x = ((Circle) s)._centerX;
				y = ((Circle) s)._centerY;
				r = ((Circle) s)._radius;
			}
		}
		for (AbstractShape s : outsideShape)
		{
			s.outsideClip(c);
			if (Circle.class.isInstance(s))
			{
				r+= ((Circle) s)._radius;
			}
		}
		
		
		Rect bounds = c.getClipBounds();
		_x = bounds.centerX();
		
		r/=2;
		double temp = r*r - ((double)(_x - x)) * ((double)(_x - x));
		temp = Math.sqrt(temp);
		
		double d = y + temp; 
		boolean flag = true; 
		for (AbstractShape s : insideShape)
		{
			s.insideClip(c);
			if (Rectangle.class.isInstance(s))
			{
				if (!(((Rectangle) s).containsPoint(_x, (int)d)))
				{
					flag = false;
					break;
				}
			}
		}
		
		if (flag) 
			_y = (int)d;
		else
			_y = (int)(y - temp);
		
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
