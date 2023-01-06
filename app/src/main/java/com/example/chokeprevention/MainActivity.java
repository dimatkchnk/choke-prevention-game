package com.example.chokeprevention;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

/**
 * Main menu class
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Initialization method, set view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * Start game
     * @param view Current view
     */
    public void startGame(View view) {
        GameView gameView = new GameView(this);
        setContentView(gameView);
    }

    /**
     * Close game
     * @param view Current view
     */
    public void exit(View view) {
        finish();
    }
}