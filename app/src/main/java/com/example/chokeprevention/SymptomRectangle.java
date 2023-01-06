package com.example.chokeprevention;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Big rectangle class for humans in second level
 */
public class SymptomRectangle {

    /**
     * Position
     */
    float rectangleX, rectangleY;

    Bitmap symptomRectangle;

    /**
     * Is human inside?
     */
    boolean filled = false;

    /**
     * Constructor that define bitmap for rectangle
     * @param context is used to have access to resources
     * @param rectangleX X value on screen
     * @param rectangleY Y value on screen
     */
    SymptomRectangle (Context context, float rectangleX, float rectangleY) {

        this.rectangleX = rectangleX;
        this.rectangleY = rectangleY;

        this.symptomRectangle = BitmapFactory.decodeResource(context.getResources(), R.drawable.symptom_rectangle);
    }

    public Bitmap getRectangle() { return this.symptomRectangle; }

    public int getRectangleWidth() { return this.symptomRectangle.getWidth(); }

    public int getRectangleHeight() { return this.symptomRectangle.getHeight(); }
}
