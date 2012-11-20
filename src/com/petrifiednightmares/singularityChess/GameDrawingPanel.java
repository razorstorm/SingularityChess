package com.petrifiednightmares.singularityChess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.utilities.SingularBitmapFactory;

public class GameDrawingPanel extends SurfaceView implements OnTouchListener,
		SurfaceHolder.Callback
{
	PanelThread _thread;
	public static Bitmap background;

	public static int WIDTH, HEIGHT, MIN_DIMENSION, UNIT, PADDING, PIECE_SIZE, TOP_PADDING,
			CIRCLE_RADIUS_DIFFERENCE, TOP_BAR_BOTTOM, BORDER_WIDTH;

	public static Paint darkPaint, lightPaint, highlightPaint, attackPaint, piecePaint, labelPaint,
			flashPaint, kingThreatenPaint, turnNamePaint, topBarPaint, topBarTexturePaint,
			turnNameWhitePaint, turnNameBlackPaint,borderPaint;
	private static Bitmap _darkTexture, _lightTexture, _topBarTexture,_borderTexture;

	private static Bitmap _drawingBitmap;
	private static Canvas _drawingCanvas;

	GameActivity gameActivity;

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
		PADDING = 5 * UNIT;
		PIECE_SIZE = 10 * UNIT;
		TOP_PADDING = 70; // Derived from: 10 * UNIT;
		TOP_BAR_BOTTOM = 56; // Derived from: 8 * UNIT;
		CIRCLE_RADIUS_DIFFERENCE = 10 * UNIT; // 12
		BORDER_WIDTH = (WIDTH/2-PADDING) - 4*CIRCLE_RADIUS_DIFFERENCE;

		Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
		_drawingBitmap = Bitmap.createBitmap(WIDTH, HEIGHT, conf);
		_drawingCanvas = new Canvas(_drawingBitmap);

		_darkTexture = BitmapFactory.decodeResource(getResources(), R.drawable.wood_1);
		BitmapShader darkShader = new BitmapShader(_darkTexture, Shader.TileMode.REPEAT,
				Shader.TileMode.REPEAT);

		darkPaint = new Paint();
		darkPaint.setShader(darkShader);
		darkPaint.setAntiAlias(true);
		darkPaint.setFilterBitmap(true);

		_lightTexture = BitmapFactory.decodeResource(getResources(), R.drawable.retina_wood_1);
		BitmapShader lightShader = new BitmapShader(_lightTexture, Shader.TileMode.REPEAT,
				Shader.TileMode.REPEAT);

		_topBarTexture = SingularBitmapFactory.buildBitmap(getResources(), R.drawable.wild_oliva);
		BitmapShader topBarShader = new BitmapShader(_topBarTexture, Shader.TileMode.REPEAT,
				Shader.TileMode.REPEAT);

		topBarTexturePaint = new Paint();
		topBarTexturePaint.setShader(topBarShader);
		topBarTexturePaint.setAntiAlias(true);
		topBarTexturePaint.setFilterBitmap(true);

		lightPaint = new Paint();
		lightPaint.setShader(lightShader);
		lightPaint.setAntiAlias(true);
		lightPaint.setFilterBitmap(true);
		
		_borderTexture = BitmapFactory.decodeResource(getResources(), R.drawable.leather);
		BitmapShader borderShader = new BitmapShader(_borderTexture, Shader.TileMode.REPEAT,
				Shader.TileMode.REPEAT);
		borderPaint = new Paint();
		borderPaint.setShader(borderShader);
		borderPaint.setAntiAlias(true);
		borderPaint.setFilterBitmap(true);
		borderPaint.setShadowLayer(20, 20, 20, Color.BLACK);

		highlightPaint = new Paint();
		highlightPaint.setColor(Color.rgb(36, 109, 218));
		highlightPaint.setAlpha(200);
		highlightPaint.setAntiAlias(true);

		flashPaint = new Paint();
		flashPaint.setColor(Color.rgb(0, 255, 0));
		flashPaint.setAlpha(200);
		flashPaint.setAntiAlias(true);

		attackPaint = new Paint();
		attackPaint.setColor(Color.rgb(205, 92, 92));
		attackPaint.setAntiAlias(true);

		kingThreatenPaint = new Paint();
		kingThreatenPaint.setColor(Color.rgb(255, 127, 0));
		kingThreatenPaint.setAntiAlias(true);

		piecePaint = new Paint();
		piecePaint.setAntiAlias(true);
		piecePaint.setShadowLayer(70, 3, 3, Color.BLACK);

		labelPaint = new Paint();
		labelPaint.setColor(Color.RED);

		topBarPaint = new Paint();
		topBarPaint.setAntiAlias(true);
		topBarPaint.setShader(new LinearGradient(0, 0, 0, TOP_BAR_BOTTOM, Color.WHITE, Color.rgb(
				50, 50, 50), Shader.TileMode.MIRROR));
		topBarPaint.setAlpha(150);

		turnNamePaint = new Paint();
		turnNamePaint.setColor(Color.WHITE);
		turnNamePaint.setShadowLayer(2, 0, 0, Color.BLACK);
		turnNamePaint.setTextSize(25);
		turnNamePaint.setAntiAlias(true);

		turnNameBlackPaint = new Paint();
		turnNameBlackPaint.setColor(Color.BLACK);
		turnNameBlackPaint.setAntiAlias(true);

		turnNameWhitePaint = new Paint();
		turnNameWhitePaint.setColor(Color.WHITE);
		turnNameWhitePaint.setAntiAlias(true);

		background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		float scaleWidth = ((float) WIDTH) / background.getWidth();
		float scaleHeight = ((float) HEIGHT) / background.getHeight();
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);

		background = Bitmap.createBitmap(background, 0, 0, background.getWidth(),
				background.getHeight(), matrix, false);

		game = new Game(this);
		this.setOnTouchListener(this);
	}

	public void setGameActivity(GameActivity g)
	{
		this.gameActivity = g;
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

	public void displayMessage(String message)
	{
		gameActivity.displayMessage(message);
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