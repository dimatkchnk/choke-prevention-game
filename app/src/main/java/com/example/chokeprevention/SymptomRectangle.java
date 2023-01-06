package com.example.chokeprevention;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class SymptomRectangle {

    float rectangleX, rectangleY;
    Bitmap symptomRectangle;
    boolean filled = false;
    SymptomRectangle (Context context, float rectangleX, float rectangleY) {

        this.rectangleX = rectangleX;
        this.rectangleY = rectangleY;

        this.symptomRectangle = BitmapFactory.decodeResource(context.getResources(), R.drawable.symptom_rectangle);
    }

    public Bitmap getRectangle() { return this.symptomRectangle; }

    public int getRectangleWidth() { return this.symptomRectangle.getWidth(); }

    public int getRectangleHeight() { return this.symptomRectangle.getHeight(); }
}
