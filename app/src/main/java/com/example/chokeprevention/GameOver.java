package com.example.chokeprevention;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {



    TextView tvPoints;
    SharedPreferences sharedPreferences;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Different game-over screens depending on current user level
        if (LevelPassed.currLevel == 0) {
            setContentView(R.layout.game_over);
            tvPoints = findViewById(R.id.tvPoints);
            int points = getIntent().getExtras().getInt("points");
            tvPoints.setText("" + points);
            sharedPreferences = getSharedPreferences("my_pref", 0);

        } else if (LevelPassed.currLevel > 0) {
            setContentView(R.layout.second_game_over);
            LevelPassed.currLevel = 0;
        }

    }

    // Restart button function (start from level one)
    public void restart(View view) {

        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();


    }

    // Exit button function
    public void exit(View view) { finish(); }

}
