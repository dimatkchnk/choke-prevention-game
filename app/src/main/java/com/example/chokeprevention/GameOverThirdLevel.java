package com.example.chokeprevention;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOverThirdLevel extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.second_game_over);
    }

    // Restart button function (start from level one)
    public void restart(View view) {

        LevelPassed.currLevel = 0;
        Intent intent = new Intent(GameOverThirdLevel.this, MainActivity.class);
        startActivity(intent);
        finish();


    }

    // Exit button function
    public void exit(View view) {
        finish();
    }
}
