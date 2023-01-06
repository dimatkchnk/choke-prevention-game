package com.example.chokeprevention;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Effect {

    Bitmap[] effect = new Bitmap[4];
    int effectFrame = 0;
    float effectX, effectY;

    public Effect(Context context) {
        effect[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_frame1);
        effect[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_frame2);
        effect[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_frame3);
        effect[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_frame4);
    }

    public Bitmap getEffect(int effectFrame) {
        return effect[effectFrame];
    }
}
