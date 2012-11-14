package com.petrifiednightmares.singularityChess;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class GameActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_game);
        
        
        GameDrawingPanel gdp = (GameDrawingPanel) findViewById(R.id.canvas);
        gdp.setGameActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game, menu);
        return true;
    }
    
    
    public void displayMessage(String message)
    {
    	Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    
}
