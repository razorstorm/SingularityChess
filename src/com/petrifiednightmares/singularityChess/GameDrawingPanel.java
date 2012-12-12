package com.petrifiednightmares.singularityChess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.petrifiednightmares.singularityChess.logic.Board;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.ui.GameUI;
import com.petrifiednightmares.singularityChess.ui.SUI;
import com.petrifiednightmares.singularityChess.utilities.SingularBitmapFactory;

public class GameDrawingPanel extends SurfaceView implements OnTouchListener,
		SurfaceHolder.Callback
{
	PanelThread _thread;
	public static Bitmap background;

	private static Bitmap _drawingBitmap;
	private static Canvas _drawingCanvas;

	GameActivity gameActivity;

	private Context _context;

	public Game game;
	public Board board;
	public GameUI gui;

	ScrollView movesView;

	public GameDrawingPanel(Context context, AttributeSet aSet)
	{
		super(context, aSet);
		this._context = context;
		getHolder().addCallback(this);

		Display disp = ((WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();

		SUI.setup(disp.getWidth(), disp.getHeight(), getResources(), getContext());

		Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
		_drawingBitmap = Bitmap.createBitmap(SUI.WIDTH, SUI.HEIGHT, conf);
		_drawingCanvas = new Canvas(_drawingBitmap);

		background = SingularBitmapFactory.buildScaledBitmap(getResources(), R.drawable.background,
				SUI.WIDTH, SUI.HEIGHT);

		this.setOnTouchListener(this);

	}

	public void initialize(GameActivity g, ScrollView movesView)
	{
		this.gameActivity = g;
		this.movesView = movesView;
		game = new Game(this);

		gui = new GameUI(this, game, movesView);

		board = new Board(this, game);
		
		game.initialize(board,gui);
	}

	@Override
	public void onDraw(Canvas canvas)
	{
		if (!gui.PROMPT_WAITING)
		{
			// do drawing stuff here.
			game.onDraw(_drawingCanvas);
			board.onDraw(_drawingCanvas);
		}
		gui.onDraw(_drawingCanvas);

		canvas.drawBitmap(_drawingBitmap, 0, 0, null);
	}

	public void redrawAll()
	{
		game.redraw();
		board.redraw();
		gui.redraw();
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
			if (!gui.PROMPT_WAITING)
			{
				board.onClick((int) event.getX(), (int) event.getY());
				game.onClick((int) event.getX(), (int) event.getY());
			}
			gui.onClick((int) event.getX(), (int) event.getY());
		}

		return true;
	}

	public void displayMessage(String message)
	{
		gameActivity.displayMessage(message);
	}

	public Context get_context()
	{
		return _context;
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