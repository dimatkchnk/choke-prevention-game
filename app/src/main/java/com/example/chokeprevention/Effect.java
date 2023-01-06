package com.example.chokeprevention;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Effect class to show when element collide with main character
 */
public class Effect {

    /**
     * Array to keep frames of explosion effect
     */
    Bitmap[] effect = new Bitmap[4];

    /**
     * Current frame
     */
    int effectFrame = 0;

    /**
     * Where to appear
     */
    float effectX, effectY;


    /**
     * Constructor to define bitmaps for every frame
     * @param context
     */
    public Effect(Context context) {
        effect[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_frame1);
        effect[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_frame2);
        effect[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_frame3);
        effect[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_frame4);
    }


    /**
     * Get bitmap of current frame
     * @param effectFrame which frame to return
     * @return bitmap of current frame
     */
    public Bitmap getEffect(int effectFrame) {
        return effect[effectFrame];
    }
}
