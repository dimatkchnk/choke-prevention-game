package com.example.chokeprevention;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Game over menu class
 */
public class GameOver extends AppCompatActivity {

    /**
     * Place where to show points (connect with TextView in game_over.xml)
     */
    TextView tvPoints;
    SharedPreferences sharedPreferences;

    /**
     * Initialization method. Different game-over views depending on current user level
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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

    /**
     * Restart button function (start from level one)
     * @param view Current level view
     */
    public void restart(View view) {

        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();


    }

    /**
     * Exit button function
     * @param view Current level view.
     */
    public void exit(View view) { finish(); }

}
