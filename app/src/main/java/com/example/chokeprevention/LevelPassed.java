package com.example.chokeprevention;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LevelPassed extends AppCompatActivity {

    public static int currLevel = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (currLevel < 2) setContentView(R.layout.level_passed);
        else setContentView(R.layout.game_end);



    }

    // Start next level depending on current level
    public void nextLevel(View view) {
        if (currLevel == 0) {
            GameView2 gameView2 = new GameView2(this);
            setContentView(gameView2);
            currLevel++;
        } else {
            GameView3 gameView3 = new GameView3(this);
            setContentView(gameView3);
            currLevel++;
        }

    }

    public void exit(View view) {
        finish();
    }
}
