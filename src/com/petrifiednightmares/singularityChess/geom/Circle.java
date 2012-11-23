package com.petrifiednightmares.singularityChess.geom;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Region;

public class Circle extends AbstractShape
{

	int centerX,centerY;
	int radius;
	Path path;
	
	public Circle(int centerX, int centerY, int radius)
	{
		this.centerX=centerX;
		this.centerY=centerY;
		this.radius=radius;
		
		path = new Path();
		path.addCircle(centerX,centerY,radius,Direction.CW);
	}

	@Override
	public boolean containsPoint(int x, int y)
	{
		return distance(x,y,centerX,centerY)<=radius;
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
