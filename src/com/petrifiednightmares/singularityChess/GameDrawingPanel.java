package com.petrifiednightmares.singularityChess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.petrifiednightmares.singularityChess.logic.Game;

public class GameDrawingPanel extends SurfaceView implements SurfaceHolder.Callback
{
	PanelThread _thread;
	Bitmap _background;

	public static int WIDTH, HEIGHT, MIN_DIMENSION, UNIT, PADDING;

	
	public static Paint darkPaint, lightPaint, highlightPaint,attackPaint,piecePaint;

	Game game;

	public GameDrawingPanel(Context context, AttributeSet aSet)
	{
		super(context, aSet);
		getHolder().addCallback(this);

		Display disp = ((WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();

		WIDTH = disp.getWidth();
		HEIGHT = disp.getHeight();
		MIN_DIMENSION = Math.min(WIDTH, HEIGHT);
		UNIT = (int) (MIN_DIMENSION / 100.0);
		PADDING = 4 * UNIT;

		darkPaint = new Paint();
		darkPaint.setColor(Color.rgb(50, 50, 50));
		darkPaint.setAntiAlias(true);

		lightPaint = new Paint();
		lightPaint.setColor(Color.rgb(200, 200, 200));
		lightPaint.setAntiAlias(true);
		
		highlightPaint = new Paint();
		highlightPaint.setColor(Color.rgb(224,255,255));
		highlightPaint.setAntiAlias(true);
		
		attackPaint = new Paint();
		attackPaint.setColor(Color.rgb(205,92,92));
		attackPaint.setAntiAlias(true);
		
		piecePaint = new Paint();
		piecePaint.setColor(Color.GREEN); // TODO fix
		piecePaint.setAntiAlias(true);


		_background = BitmapFactory.decodeResource(getResources(), R.drawable.felt);

		game = new Game(this);
	}

	@Override
	public void onDraw(Canvas canvas)
	{
		// draw background
		canvas.drawBitmap(_background, 0, 0, null);

		// do drawing stuff here.
		// canvas.drawBitmap(_bitmap, 0, 0, _paint);
		game.onDraw(canvas);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{

	}

	public void surfaceCreated(SurfaceHolder holder)
	{

		setWillNotDraw(false); // Allows us to use invalidate() to call onDraw()

		_thread = new PanelThread(getHolder(), this); // Start the thread that
		_thread.setRunning(true); // will make calls to
		_thread.start(); // onDraw()
	}

	public void surfaceDestroyed(SurfaceHolder holder)
	{
		try
		{
			_thread.setRunning(false); // Tells thread to stop
			_thread.join(); // Removes thread from mem.
		} catch (InterruptedException e)
		{
		}
	}

	public void updateGame()
	{
		// TODO Auto-generated method stub
		
	}

}

class PanelThread extends Thread
{
	private SurfaceHolder _surfaceHolder;
	private GameDrawingPanel _panel;
	private boolean _run = false;

	private final static int FRAMES_PER_SECOND = 25;
	private final static int MILLISECONDS_PER_FRAME = 1000 / FRAMES_PER_SECOND;

	public PanelThread(SurfaceHolder surfaceHolder, GameDrawingPanel panel)
	{
		_surfaceHolder = surfaceHolder;
		_panel = panel;
	}

	public void setRunning(boolean run)
	{ // Allow us to stop the thread
		_run = run;
	}

	@Override
	public void run()
	{
		long sleepTime = 0;
		long nextGameTick = System.currentTimeMillis();
		
		Canvas c;
		while (_run)
		{ // When setRunning(false) occurs, _run is
			c = null; // set to false and loop ends, stopping thread
			try
			{
				c = _surfaceHolder.lockCanvas(null);
				synchronized (_surfaceHolder)
				{
					// Insert methods to modify positions of items in onDraw()
					_panel.updateGame();
					_panel.postInvalidate();
				}
			} finally
			{
				if (c != null)
				{
					_surfaceHolder.unlockCanvasAndPost(c);
				}
			}
			
			nextGameTick += MILLISECONDS_PER_FRAME;
			sleepTime = nextGameTick - System.currentTimeMillis();
			if(sleepTime >= 0)
			{
				try
				{
					sleep(sleepTime, 0);
				} catch (InterruptedException e)
				{
					continue;
				}
			}
			else
			{
				//we're behind, fuck it.
				System.out.println("behind!");
				nextGameTick = System.currentTimeMillis();
			}
		}
	}
}