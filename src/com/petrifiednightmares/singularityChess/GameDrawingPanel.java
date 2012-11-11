package com.petrifiednightmares.singularityChess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

import com.petrifiednightmares.singularityChess.logic.Game;

public class GameDrawingPanel extends SurfaceView implements OnTouchListener,
		SurfaceHolder.Callback
{
	PanelThread _thread;
	public static Bitmap background;

	public static int WIDTH, HEIGHT, MIN_DIMENSION, UNIT, PADDING;

	public static Paint darkPaint, lightPaint, highlightPaint, attackPaint, piecePaint,labelPaint;
	private static Bitmap _darkTexture, _lightTexture;

	private static Bitmap _drawingBitmap;
	private static Canvas _drawingCanvas;

	Game game;

	@SuppressWarnings("deprecation")
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

		Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
		_drawingBitmap = Bitmap.createBitmap(WIDTH, HEIGHT, conf);
		_drawingCanvas = new Canvas(_drawingBitmap);

		_darkTexture = BitmapFactory.decodeResource(getResources(), R.drawable.wood_2);
		BitmapShader darkShader = new BitmapShader(_darkTexture, Shader.TileMode.REPEAT,
				Shader.TileMode.REPEAT);

		darkPaint = new Paint();
		darkPaint.setShader(darkShader);
		// darkPaint.setColor(Color.rgb(50, 50, 50));
		darkPaint.setAntiAlias(true);
		darkPaint.setFilterBitmap(true);

		_lightTexture = BitmapFactory.decodeResource(getResources(), R.drawable.retina_wood_2);
		BitmapShader lightShader = new BitmapShader(_lightTexture, Shader.TileMode.REPEAT,
				Shader.TileMode.REPEAT);

		lightPaint = new Paint();
		// lightPaint.setColor(Color.rgb(200, 200, 200));
		lightPaint.setShader(lightShader);
		lightPaint.setAntiAlias(true);
		lightPaint.setFilterBitmap(true);

		highlightPaint = new Paint();
		highlightPaint.setColor(Color.rgb(36, 109, 218));
		highlightPaint.setAlpha(200);
		highlightPaint.setAntiAlias(true);

		attackPaint = new Paint();
		attackPaint.setColor(Color.rgb(205, 92, 92));
		attackPaint.setAntiAlias(true);

		piecePaint = new Paint();
		piecePaint.setColor(Color.BLACK); 
		piecePaint.setAntiAlias(true);
		piecePaint.setTypeface(Typeface.create("Tahoma",Typeface.BOLD));
		piecePaint.setTextSize(25);

		labelPaint = new Paint();
		labelPaint.setColor(Color.RED); 
		
		background = BitmapFactory.decodeResource(getResources(), R.drawable.felt);

		game = new Game(this);
		this.setOnTouchListener(this);
	}

	@Override
	public void onDraw(Canvas canvas)
	{
		// do drawing stuff here.
		// canvas.drawBitmap(_bitmap, 0, 0, _paint);
		game.onDraw(_drawingCanvas);

		canvas.drawBitmap(_drawingBitmap, 0, 0, null);
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

	public boolean onTouch(View v, MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			game.onClick((int) event.getX(), (int) event.getY());
		}

		return true;
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
			if (sleepTime >= 0)
			{
				try
				{
					sleep(sleepTime, 0);
				} catch (InterruptedException e)
				{
					continue;
				}
			} else
			{
				// we're behind, fuck it.
				// System.out.println("behind!");
				nextGameTick = System.currentTimeMillis();
			}
		}
	}
}