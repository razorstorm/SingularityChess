package com.petrifiednightmares.singularityChess.geom;

import android.graphics.Canvas;
import android.graphics.Region;

public class Rectangle extends AbstractShape
{
	int _topLeftX, _topLeftY;
	int _right,_bottom;

	public Rectangle(int topLeftX, int topLeftY, int right, int bottom)
	{
		this._topLeftX = topLeftX;
		this._topLeftY = topLeftY;
		this._right = right;
		this._bottom = bottom;
	}

	@Override
	public boolean containsPoint(int x, int y)
	{
		return x > _topLeftX && x <= _right && y > _topLeftY && y <= _bottom;
	}

	@Override
	public void insideClip(Canvas c)
	{
		c.clipRect(_topLeftX, _topLeftY, _right, _bottom,Region.Op.INTERSECT);
	}

	@Override
	public void outsideClip(Canvas c)
	{
		c.clipRect(_topLeftX, _topLeftY, _right, _bottom,Region.Op.DIFFERENCE);
	}

}
