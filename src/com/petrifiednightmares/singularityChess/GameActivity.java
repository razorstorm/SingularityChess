package com.petrifiednightmares.singularityChess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.InterstitialAd;
import com.petrifiednightmares.singularityChess.revenue.AdMob;
import com.petrifiednightmares.singularityChess.ui.Preferences;

public class GameActivity extends Activity implements OnClickListener, AdListener
{

	GameDrawingPanel		gdp;
	private InterstitialAd	interstitial;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_game);

		gdp = (GameDrawingPanel) findViewById(R.id.canvas);

		ScrollView movesView = (ScrollView) findViewById(R.id.movesView);

		// Decide whether to start new game or resume
		boolean resume = false;
		Intent i = getIntent();

		Bundle b = getIntent().getExtras();

		if (i.hasExtra("resume"))
		{
			resume = b.getBoolean("resume", false);
		}

		if (resume)
		{
			gdp.resume(this, movesView);
			doAds();
		}
		else
		{
			int gameType = b.getInt("gameType");
			gdp.initialize(this, movesView, gameType);
		}

	}

	private void doAds()
	{

		// Create the interstitial
		interstitial = new InterstitialAd(this, AdMob.PUBLISHER_ID);

		// Create ad request
		AdRequest adRequest = new AdRequest();

		// Set test mode for developer devices
		adRequest.addTestDevice(AdRequest.TEST_EMULATOR);               // Emulator
		adRequest.addTestDevice("07D8BDC5588893E0D84EC2165FDEA282");                      // Test Android Device

		// Begin loading your interstitial
		interstitial.loadAd(adRequest);

		// Set Ad Listener to use the callbacks below
		interstitial.setAdListener(this);

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
					if (gdp != null)
						gdp.redraw();
				}
				else
				{
					item.setChecked(true);
					Preferences.SHOW_SQUARE_LABELS = true;
					if (gdp != null)
						gdp.redraw();
				}
				return true;
			case R.id.pref_mute:
				if (item.isChecked())
				{
					item.setChecked(false);
					Preferences.MUTE = false;
				}
				else
				{
					item.setChecked(true);
					Preferences.MUTE = true;
				}
				return true;
			case R.id.show_help:
				if (gdp != null)
				{
					gdp.showInstructions();
				}
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK))
		{
			new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(R.string.quit).setMessage(R.string.really_quit)
					.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							// Stop the activity
							GameActivity.this.finish();
						}

					}).setNegativeButton(R.string.no, null).show();

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void displayMessage(String message)
	{
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	public void onClick(View v)
	{
		// TODO Auto-generated method stub

	}

	public void onDismissScreen(Ad arg0)
	{
		// TODO Auto-generated method stub

	}

	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1)
	{
		// TODO Auto-generated method stub

	}

	public void onLeaveApplication(Ad arg0)
	{
		// TODO Auto-generated method stub

	}

	public void onPresentScreen(Ad arg0)
	{
		// TODO Auto-generated method stub

	}

	public void onReceiveAd(Ad ad)
	{
		if (ad == interstitial)
		{
			interstitial.show();
		}

	}

}
