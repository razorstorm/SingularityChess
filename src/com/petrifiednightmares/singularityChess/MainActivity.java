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

public class MainActivity extends Activity
{
	Button singlePlayerButton, resumeButton;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		this.singlePlayerButton = (Button) findViewById(R.id.vs_human);
		this.singlePlayerButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				startSinglePlayerGame();
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
	}

	private void startSinglePlayerGame()
	{
		new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(R.string.start_new_game).setMessage(R.string.really_start_new_game)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						Intent singlePlayerGameIntent = new Intent(MainActivity.this,
								GameActivity.class);

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
