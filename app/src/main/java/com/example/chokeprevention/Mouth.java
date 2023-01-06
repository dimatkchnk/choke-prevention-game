package com.example.chokeprevention;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Mouth {

    Bitmap[] mouth = new Bitmap[2];
    int mouthFrame = 0;
    int mouthX, mouthY;

    public Mouth(Context context) {
        mouth[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.closed_mouth);
        mouth[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.open_mouth);
    }

    public Bitmap getMouth(int mouthFrame) {
        return mouth[mouthFrame];
    }
}
