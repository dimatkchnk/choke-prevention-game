package com.example.chokeprevention;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


/**
 * Small rectangles class, extending big rectangles
 * @see SymptomRectangle
 */
public class SmallRectangle extends SymptomRectangle{

    Bitmap smallRectangle;

    /**
     * Specify different pos parameter for rectangles
     */
    static int counter = 0;

    /**
     * Is used to check if letter sequence is correct
     */
    int pos;

    /**
     * Constructor that attributes bitmap to rectangle instance
     * @param context is used to have access for resources
     * @param rectangleX X value on screen
     * @param rectangleY Y value on screen
     */
    SmallRectangle(Context context, float rectangleX, float rectangleY) {
        super(context, rectangleX, rectangleY);

        this.smallRectangle = BitmapFactory.decodeResource(context.getResources(), R.drawable.small_rectangle);
        if (counter == 0) {
            this.pos = 0;
        } else if (counter == 1) {
            this.pos = 1;
        }else if (counter == 2) {
            this.pos = 2;
        }else if (counter == 3) {
            this.pos = 3;
        }
        counter++;
    }

    public Bitmap getRectangle() { return this.smallRectangle; }

    public int getRectangleWidth() { return this.smallRectangle.getWidth(); }

    public int getRectangleHeight() { return this.smallRectangle.getHeight(); }
}
