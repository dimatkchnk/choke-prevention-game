package com.example.chokeprevention;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Letter {

    Bitmap letter;
    int letterX, letterY;
    private Random random;
    boolean movable = true;
    String whichLetter;
    static int counter = 0;

    Letter(Context context) {
        random = new Random();
        setPosition(context);
    }

    public Bitmap getLetter() { return this.letter; }

    public int getLetterWidth() { return this.letter.getWidth(); }

    public int getLetterHeight() { return this.letter.getHeight(); }

    public void setPosition(Context context) {

        if (counter == 0) {
            this.letter = BitmapFactory.decodeResource(context.getResources(), R.drawable.h_letter);
            this.whichLetter = "h";
        } else if (counter == 1) {
            this.letter = BitmapFactory.decodeResource(context.getResources(), R.drawable.e_letter);
            this.whichLetter = "e";
        } else if (counter == 2) {
            this.letter = BitmapFactory.decodeResource(context.getResources(), R.drawable.l_letter);
            this.whichLetter = "l";
        } else if (counter == 3) {
            this.letter = BitmapFactory.decodeResource(context.getResources(), R.drawable.p_letter);
            this.whichLetter = "p";
        }

        counter++;

        this.letterX =  random.nextInt(GameView2.dWidth - getLetterWidth());
        this.letterY = ThreadLocalRandom.current().nextInt(300, GameView2.dHeight - getLetterHeight() - 500);
    }

}
