package com.petrifiednightmares.singularityChess.geom;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

import com.petrifiednightmares.singularityChess.ui.SUI;

public class ComplexShape
{
	// shapes that define where inside is. intersected
	ArrayList<AbstractShape> insideShape;

	// These shapes are regions where the complexshape does NOT lie.
	ArrayList<AbstractShape> outsideShape;

	Rect boundingRect;
	Rect sideRect;
	Circle innerCircle;
	Circle outterCircle;
	RectF bigCircle;
	RectF smallCircle;
	RectF bounds;

	short top = -1;
	boolean right;
	Path p;

	private int _x, _y;

	public ComplexShape()
	{
		insideShape = new ArrayList<AbstractShape>();
		outsideShape = new ArrayList<AbstractShape>();
		sideRect = new Rect(0, 0, SUI.WIDTH, SUI.HEIGHT);
	}

	public void setOutterCircle(Circle c)
	{
		this.outterCircle = c;
		bigCircle = new RectF(c.centerX - c.radius, c.centerY - c.radius, c.centerX + c.radius,
				c.centerY + c.radius);
	}

	public void setInnerCircle(Circle c)
	{
		this.innerCircle = c;
		smallCircle = new RectF(c.centerX - c.radius, c.centerY - c.radius, c.centerX + c.radius,
				c.centerY + c.radius);

	}

	public void setBoundingRect(Rect r)
	{
		this.boundingRect = r;
	}

	public void setTop(int t)
	{
		this.top = (short) t;
		switch (top)
		{
		case 1:
			sideRect = new Rect(0, 0, SUI.WIDTH, SUI.HEIGHT_CENTER);
			break;
		case 0:
			sideRect = new Rect(0, SUI.HEIGHT_CENTER, SUI.WIDTH, SUI.HEIGHT);
			break;

		}
	}

	public void setRight(boolean r)
	{
		this.right = r;
	}


	public boolean containsPoint(int x, int y)
	{
		if (innerCircle != null)
			return (outterCircle.containsPoint(x, y) && boundingRect.contains(x, y)
					&& sideRect.contains(x, y) && !innerCircle.containsPoint(x, y));
		else
			return (outterCircle.containsPoint(x, y) && boundingRect.contains(x, y) && sideRect
					.contains(x, y));
	}


	private void computeCenter()
	{
		_x = (int) boundingRect.centerX();
		int averageRadius = innerCircle.radius + outterCircle.radius;
		averageRadius /= 2;

		int centerX = innerCircle.centerX;
		int centerY = innerCircle.centerY;

		double temp = averageRadius * averageRadius - ((double) (_x - centerX))
				* ((double) (_x - centerX));
		temp = Math.sqrt(temp);

		double d = centerY + temp;

		if (top == 0)
			_y = (int) d;
		else if (top == 1)
			_y = (int) (centerY - temp);
		else
			_y = centerY;

	}

	public void setupPath()
	{
		int h = SUI.WIDTH / 2;
		int k = SUI.HEIGHT_CENTER;

		int x = boundingRect.left;
		int r = outterCircle.radius;
		int r2 = innerCircle.radius;
		int x2 = boundingRect.right;

		p = new Path();
		if (top == 1)
			p.moveTo(x, (float) (k - Math.sqrt(-(h * h) + 2 * h * x + r * r - (x * x))));
		else if (top == 0)
			p.moveTo(x, (float) (k + Math.sqrt(-(h * h) + 2 * h * x + r * r - (x * x))));
		else
		{
			if (right)
			{
				p.moveTo(x, (float) (k + Math.sqrt(-(h * h) + 2 * h * x + r * r - (x * x))));

			} else
			{
				p.moveTo(x2, (float) (k + Math.sqrt(-(h * h) + 2 * h * x2 + r * r - (x2 * x2))));
			}
		}

		float angle = Math.abs((float) Math.toDegrees(Math.atan(Math.sqrt(-(h * h) + 2 * h * x + r
				* r - (x * x))
				/ (h - x))));

		float endAngle = (float) Math.toDegrees(Math.atan(Math.sqrt(-(h * h) + 2 * h * x2 + r * r
				- (x2 * x2))
				/ (h - x2)));
		endAngle = Math.abs(endAngle);

		if (top == 1)
		{
			if (right)
				p.arcTo(bigCircle, -angle, Math.abs(endAngle - angle));
			else
				p.arcTo(bigCircle, 180 + angle, Math.abs(endAngle - angle));

		} else if (top == 0)
		{
			if (right)
				p.arcTo(bigCircle, angle, -Math.abs(endAngle - angle));
			else
				p.arcTo(bigCircle, 180 - angle, -Math.abs(endAngle - angle));
		} else
		{
			if (right)
				p.arcTo(bigCircle, angle, -2 * Math.abs(endAngle - angle));
			else
				p.arcTo(bigCircle, 180 - endAngle, 2 * Math.abs(endAngle - angle));
		}

		if (top != -1)
		{
			if (top == 1)
				p.lineTo(x2, (float) (k - Math.sqrt(-(h * h) + 2 * h * x2 + r2 * r2 - (x2 * x2))));
			else if (top == 0)
				p.lineTo(x2, (float) (k + Math.sqrt(-(h * h) + 2 * h * x2 + r2 * r2 - (x2 * x2))));

			angle = (float) Math.abs(Math.toDegrees(Math.atan(Math.sqrt(-(h * h) + 2 * h * x2 + r2
					* r2 - (x2 * x2))
					/ (h - x2))));

			endAngle = (float) Math.abs(Math.toDegrees(Math.atan(Math.sqrt(-(h * h) + 2 * h * x
					+ r2 * r2 - (x * x))
					/ (h - x))));
			if (top == 1)
			{
				if (right)
					p.arcTo(smallCircle, -angle, -Math.abs(endAngle - angle));
				else
					p.arcTo(smallCircle, 180 + angle, -Math.abs(endAngle - angle));
			} else if (top == 0)
			{
				if (right)
					p.arcTo(smallCircle, angle, Math.abs(endAngle - angle));
				else
					p.arcTo(smallCircle, 180 - angle, Math.abs(endAngle - angle));
			}
		}

		bounds = new RectF();
		p.computeBounds(bounds, true);
		computeCenter();
	}

	public void onDraw(Canvas c, Paint paint)
	{
		c.drawPath(p, paint);
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
