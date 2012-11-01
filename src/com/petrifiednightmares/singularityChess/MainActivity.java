package com.petrifiednightmares.singularityChess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button singlePlayerButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        this.singlePlayerButton = (Button) findViewById(R.id.single_player);
		this.singlePlayerButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				startSinglePlayerGame();
			}
		});
    }

    
    private void startSinglePlayerGame()
    {
    	Intent singlePlayerGameIntent = new Intent(this, GameActivity.class);

		startActivity(singlePlayerGameIntent);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
