package com.example.chokeprevention;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Symptom {


    int symptomX, symptomY;
    private Bitmap symptom;
    private Random random;

    static boolean x = true;
    static int counter = 0;

    boolean isChokeSymptom;
    boolean movable = true;


    public Symptom(Context context) {

        random = new Random();
        setPosition(context);

    }

    public Bitmap getSymptom() { return this.symptom; }

    public int getSymptomWidth() { return this.symptom.getWidth(); }

    public int getSymptomHeight() { return this.symptom.getHeight(); }


    // Function that define bitmap and position (x, y) of symptom
    private void setPosition(Context context) {


        if (x) {
            if (counter == 0) {
                this.symptom = BitmapFactory.decodeResource(context.getResources(), R.drawable.choke1);
                x = false;
                this.isChokeSymptom = true;

            } else if (counter == 2) {
                this.symptom = BitmapFactory.decodeResource(context.getResources(), R.drawable.choke2);
                x = false;
                this.isChokeSymptom = true;

            } else if (counter == 4) {
                this.symptom = BitmapFactory.decodeResource(context.getResources(), R.drawable.choke3);
                x = false;
                this.isChokeSymptom = true;

            }
        } else {
            if (counter == 1) {
                this.symptom = BitmapFactory.decodeResource(context.getResources(), R.drawable.not_choke1);
                x = true;
                this.isChokeSymptom = false;

            } else if (counter == 3) {
                this.symptom = BitmapFactory.decodeResource(context.getResources(), R.drawable.not_choke2);
                x = true;
                this.isChokeSymptom = false;

            } else if (counter == 5) {
                this.symptom = BitmapFactory.decodeResource(context.getResources(), R.drawable.not_choke3);
                x = true;
                this.isChokeSymptom = false;

            }
        }

        counter++;

        symptomX = random.nextInt(GameView2.dWidth - getSymptomWidth());
        symptomY = ThreadLocalRandom.current().nextInt(300, GameView2.dHeight - getSymptomHeight() - 500);

    }
}
