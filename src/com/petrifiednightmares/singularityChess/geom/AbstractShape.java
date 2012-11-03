package com.petrifiednightmares.singularityChess.geom;

import android.graphics.Canvas;

public abstract class AbstractShape
{

	public AbstractShape()
	{

	}

	/*
	 * Difference is subtract
	 * 
	 * intersect is inner join
	 * 
	 * replace is get rid of old one
	 * 
	 * union is full join
	 * 
	 * xor is outter join
	 */


	public abstract boolean containsPoint(int x, int y);

	// use intersect
	public abstract void insideClip(Canvas c);

	// use difference
	public abstract void outsideClip(Canvas c);

}
