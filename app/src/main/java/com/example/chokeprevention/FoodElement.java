package com.example.chokeprevention;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;
import java.lang.Math;

/**
 * Food that appears and move on screen
 */
public class FoodElement {

    /**
     * Position and speed of element
     */
    int elementX, elementY, elementSpeedX, elementSpeedY;
    private Bitmap element;

    /**
     * Random to define position
     */
    private Random random;

    /**
     * Plus or minus points when collide
     */
    boolean eatable;

    /**
     * if yes - user can remove element by click on 3 times
     */
    boolean cutable;

    /**
     * To count clicks
     */
    int clicks = 0;


    /**
     * Constructor
     * @param context
     */
    public FoodElement(Context context) {

        random = new Random();
        setPosition(context);

    }

    /**
     * Get element bitmap
     * @return element bitmap
     */
    public Bitmap getElement() {
        return this.element;
    }


    public int getElementWidth() {
        return  this.element.getWidth();
    }

    public int getElementHeight() {
        return  this.element.getHeight();
    }

    /**
     * Function that define bitmap and position (x, y) of element. If element position equals to main character position, define once more
     * @param context
     */
    public void setPosition(Context context) {

        int r = (int)(Math.random() * 13);

        if (r == 0) {
            this.element = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple_element);
            this.eatable = true;
            this.clicks = 0;
        } else if (r == 1) {
            this.element = BitmapFactory.decodeResource(context.getResources(), R.drawable.cheese_element);
            this.eatable = true;
            this.clicks = 0;
        } else if (r == 2) {
            this.element = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin_element);
            this.eatable = false;
            this.clicks = 0;
        } else if (r == 3) {
            this.element = BitmapFactory.decodeResource(context.getResources(), R.drawable.chicken_element);
            this.eatable = false;
            this.cutable = true;
            this.clicks = 0;
        } else if (r == 4) {
            this.element = BitmapFactory.decodeResource(context.getResources(), R.drawable.chicken_leg_element);
            this.eatable = true;
            this.clicks = 0;
        } else if (r == 5) {
            this.element = BitmapFactory.decodeResource(context.getResources(), R.drawable.fish_bone_element);
            this.eatable = false;
            this.clicks = 0;
        } else if (r == 6) {
            this.element = BitmapFactory.decodeResource(context.getResources(), R.drawable.pen_element);
            this.eatable = false;
            this.clicks = 0;
        } else if (r == 7) {
            this.element = BitmapFactory.decodeResource(context.getResources(), R.drawable.water_melon_element);
            this.eatable = false;
            this.cutable = true;
            this.clicks = 0;
        } else if (r == 8) {
            this.element = BitmapFactory.decodeResource(context.getResources(), R.drawable.watermelon_bite_element);
            this.eatable = true;
            this.clicks = 0;
        } else if (r == 9) {
            this.element = BitmapFactory.decodeResource(context.getResources(), R.drawable.blueberry_element);
            this.eatable = true;
            this.clicks = 0;
        } else if (r == 10) {
            this.element = BitmapFactory.decodeResource(context.getResources(), R.drawable.cookie_element);
            this.eatable = true;
            this.clicks = 0;
        } else if (r == 11) {
            this.element = BitmapFactory.decodeResource(context.getResources(), R.drawable.meat_element);
            this.eatable = true;
            this.clicks = 0;
        } else if (r == 12) {
            this.element = BitmapFactory.decodeResource(context.getResources(), R.drawable.tomato_element);
            this.eatable = true;
            this.clicks = 0;
        }


        this.elementX = random.nextInt(GameView.dWidth - getElementWidth());
        this.elementY = random.nextInt(GameView.dHeight - getElementHeight());


        if (GameView.mainCharacterX < this.elementX + getElementWidth() &&
                GameView.mainCharacterX + GameView.mainCharacter.getWidth() > this.elementX &&
                GameView.mainCharacterY < this.elementY + getElementHeight() &&
                GameView.mainCharacter.getHeight() + GameView.mainCharacterY > this.elementY) {
            this.elementX = random.nextInt(GameView.dWidth - getElementWidth());
            this.elementY = random.nextInt(GameView.dHeight - getElementHeight());
        }

        this.elementSpeedX = random.nextInt(10);
        this.elementSpeedY = random.nextInt(10);

    }

}
