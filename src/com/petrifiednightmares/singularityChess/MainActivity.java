package com.petrifiednightmares.singularityChess;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.petrifiednightmares.singularityChess.io.GameIO;
import com.petrifiednightmares.singularityChess.logic.Game;

public class MainActivity extends Activity
{
	Button	vsHumanButton, resumeButton, vsCompButton;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		this.vsHumanButton = (Button) findViewById(R.id.vs_human);
		this.vsHumanButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				startVSHumanGame();
			}
		});

		this.resumeButton = (Button) findViewById(R.id.resume);
		this.resumeButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				resumeSinglePlayerGame();
			}
		});
		
		this.vsCompButton = (Button) findViewById(R.id.vs_computer);
		this.vsCompButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				startVSCompGame();
			}
		});

		GameIO.setContext(this);
	}
	
	private void startVSCompGame()
	{
		if (GameIO.hasFile(GameIO.Intention.SAVE_GAME,GameIO.StorageOption.FILE))
		{
			new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_media_play)
					.setTitle(R.string.start_new_game).setMessage(R.string.really_start_new_game)
					.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							Intent singlePlayerGameIntent = new Intent(MainActivity.this,
									GameActivity.class);

							Bundle b = new Bundle();
							b.putInt("gameType", Game.VSCOMP);
							
							singlePlayerGameIntent.putExtras(b);
							

							startActivity(singlePlayerGameIntent);
						}

					}).setNegativeButton(R.string.no_resume, new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							resumeSinglePlayerGame();
						}

					}).show();
		}
		else
		{
			Intent singlePlayerGameIntent = new Intent(MainActivity.this, GameActivity.class);
			Bundle b = new Bundle();
			b.putInt("gameType", Game.VSCOMP);
			
			singlePlayerGameIntent.putExtras(b);
			
			startActivity(singlePlayerGameIntent);
		}		
	}

	private void startVSHumanGame()
	{
		if (GameIO.hasFile(GameIO.Intention.SAVE_GAME,GameIO.StorageOption.FILE))
		{
			new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_media_play)
					.setTitle(R.string.start_new_game).setMessage(R.string.really_start_new_game)
					.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							Intent singlePlayerGameIntent = new Intent(MainActivity.this,
									GameActivity.class);

							Bundle b = new Bundle();
							b.putInt("gameType", Game.VSHUMAN);
							
							singlePlayerGameIntent.putExtras(b);
							

							startActivity(singlePlayerGameIntent);
						}

					}).setNegativeButton(R.string.no_resume, new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							resumeSinglePlayerGame();
						}

					}).show();
		}
		else
		{
			Intent singlePlayerGameIntent = new Intent(MainActivity.this, GameActivity.class);
			Bundle b = new Bundle();
			b.putInt("gameType", Game.VSHUMAN);
			
			singlePlayerGameIntent.putExtras(b);
			
			startActivity(singlePlayerGameIntent);
		}

	}

	private void resumeSinglePlayerGame()
	{
		Intent singlePlayerGameIntent = new Intent(this, GameActivity.class);
		Bundle b = new Bundle();
		b.putBoolean("resume", true);

		singlePlayerGameIntent.putExtras(b);

		startActivity(singlePlayerGameIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
