package com.petrifiednightmares.singularityChess.geom;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Region;

public class Circle extends AbstractShape
{

	int _centerX,_centerY;
	int _radius;
	Path path;
	
	public Circle(int centerX, int centerY, int radius)
	{
		this._centerX=centerX;
		this._centerY=centerY;
		this._radius=radius;
		
		path = new Path();
		path.addCircle(_centerX,_centerY,_radius,Direction.CW);
	}

	@Override
	public boolean containsPoint(int x, int y)
	{
		return distance(x,y,_centerX,_centerY)<=_radius;
	}
	
	private int distance(int x1,int y1,int x2, int y2)
	{
		return (int) Math.round(Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2)));
	}

	@Override
	public void insideClip(Canvas c)
	{
		c.clipPath(path, Region.Op.INTERSECT);
	}

	@Override
	public void outsideClip(Canvas c)
	{
		c.clipPath(path, Region.Op.DIFFERENCE);
	}

}
