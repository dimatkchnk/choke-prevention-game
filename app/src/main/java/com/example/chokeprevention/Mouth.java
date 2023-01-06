package com.example.chokeprevention;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Mouth class is used in third level
 */
public class Mouth {

    /**
     * Two bitmap frames
     */
    Bitmap[] mouth = new Bitmap[2];

    /**
     * Current frame
     */
    int mouthFrame = 0;

    /**
     * Position
     */
    int mouthX, mouthY;

    /**
     * Constructor that specifies bitmap
     * @param context is used to have access for resources
     */
    public Mouth(Context context) {
        mouth[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.closed_mouth);
        mouth[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.open_mouth);
    }

    public Bitmap getMouth(int mouthFrame) {
        return mouth[mouthFrame];
    }
}
