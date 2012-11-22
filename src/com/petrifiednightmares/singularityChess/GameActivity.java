package com.petrifiednightmares.singularityChess;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.petrifiednightmares.singularityChess.ui.Preferences;
import com.petrifiednightmares.singularityChess.ui.SUI;

public class GameActivity extends Activity implements OnClickListener
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_game);

		GameDrawingPanel gdp = (GameDrawingPanel) findViewById(R.id.canvas);

		gdp.setGameActivity(this);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_game, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.pref_disp_square_labels:
			if (item.isChecked())
			{
				item.setChecked(false);
				Preferences.SHOW_SQUARE_LABELS = false;
			} else
			{
				item.setChecked(true);
				Preferences.SHOW_SQUARE_LABELS = true;
			}
			return true;
		case R.id.pref_mute:
			if (item.isChecked())
			{
				item.setChecked(false);
				Preferences.MUTE = false;
			} else
			{
				item.setChecked(true);
				Preferences.MUTE = true;
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void displayMessage(String message)
	{
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	public void onClick(View v)
	{
		// TODO Auto-generated method stub

	}

}
