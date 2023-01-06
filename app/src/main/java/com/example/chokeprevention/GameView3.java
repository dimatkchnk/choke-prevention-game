package com.example.chokeprevention;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameView3 extends View {

    private Bitmap background;
    Bitmap redCircle, greenCircle;
    Bitmap fishBone;
    Bitmap trashBin;
    Mouth mouth;

    Rect rectBackground;
    Context context;
    Handler handler;
    Runnable runnable;
    private final long UPDATE_MILLIS = 30;
    static int dWidth, dHeight;

    float mouthX, mouthY;

    float trashBinX, trashBinY;

    float fishBoneX, fishBoneY;

    float greenCircleX;
    float greenCircleY;

    float oldX, oldY;
    float oldFishBoneX, oldFishBoneY;

    Random r = new Random();
    int interval; // time
    static int mseconds = 0; // To count time
    static boolean changeMouth = false; // Mouth bitmap frame state
    static boolean move;


    public GameView3(Context context) {
        super(context);
        this.context = context;

        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.second_background);
        redCircle = BitmapFactory.decodeResource(context.getResources(), R.drawable.red_circle);
        greenCircle = BitmapFactory.decodeResource(context.getResources(), R.drawable.green_circle);
        fishBone = BitmapFactory.decodeResource(context.getResources(), R.drawable.fishbone);
        trashBin = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_bin);



        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rectBackground = new Rect(0, 0, dWidth, dHeight);



        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        // Define random interval
        interval = r.nextInt(8000);
        if (interval <= 4000) {
            interval += 4000;
        }

        mouth = new Mouth(context);

        mouthX = (float)(dWidth/2 - mouth.getMouth(0).getWidth() / 2);
        mouthY = (float)(dHeight/2 - mouth.getMouth(0).getHeight() / 2);

        greenCircleX = (float)(dWidth/2 - greenCircle.getWidth() / 2);
        greenCircleY = (float)(dHeight/2 - greenCircle.getHeight() / 2);

        trashBinX = (float)(trashBin.getWidth());
        trashBinY = (float)(dHeight - trashBin.getHeight());

        fishBoneX = (float)(dWidth/2 - fishBone.getWidth() / 2);
        fishBoneY = (float)(dHeight/2 - fishBone.getHeight() / 2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(background, null, rectBackground, null);

        /* Check what to draw: if user didn't touch - draw first mouth frame
        *                      if user touched - draw second mouth frame, red circle and start counting time (mseconds)
        *                      if user touched and time equal to the interval passed - draw green circle and let user move fish bone  */
        if (!changeMouth) {
            canvas.drawBitmap(mouth.getMouth(0), mouthX, mouthY, null);
            mseconds = 0;
        }
        else if (changeMouth && mseconds < interval) {
            canvas.drawBitmap(mouth.getMouth(1), mouthX, mouthY, null);
            canvas.drawBitmap(redCircle, (float)(dWidth/2 - redCircle.getWidth() / 2), (float)(dHeight/2 - redCircle.getHeight() / 2), null);
            canvas.drawBitmap(fishBone, fishBoneX, fishBoneY, null);
            move = false;
        } else if (changeMouth && mseconds >= interval) {
            canvas.drawBitmap(mouth.getMouth(1), mouthX, mouthY, null);
            canvas.drawBitmap(greenCircle, greenCircleX, greenCircleY, null);
            canvas.drawBitmap(fishBone, fishBoneX, fishBoneY, null);
            canvas.drawBitmap(trashBin, trashBinX, trashBinY, null);
            move = true;

        }



        mseconds += UPDATE_MILLIS;
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {



        float touchX = event.getX();
        float touchY = event.getY();

        // If user touched mouth - open it (change mouth frame state)
        if (touchX > mouthX && touchX < mouthX + mouth.getMouth(0).getWidth() &&
                mouthY < touchY && touchY < mouthY + mouth.getMouth(0).getHeight()) {
            changeMouth = true;
        }

        // Moving fish bone
        if (touchX > fishBoneX && touchX < fishBoneX + fishBone.getWidth()
                && fishBoneY < touchY && touchY < fishBoneY + fishBone.getHeight()) {

            // Do not move fish bone if red circle on screen
            if (!move) {
                Intent intent = new Intent(context, GameOverThirdLevel.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            } else if (fishBoneX < trashBinX + trashBin.getWidth() &&       // Detect collision with trash bin
                    fishBoneX + fishBone.getWidth() > trashBinX &&
                    fishBoneY < trashBinY + trashBin.getHeight() &&
                    fishBone.getHeight() + fishBoneY > trashBinY) {

                Intent intent = new Intent(context, LevelPassed.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            } else if (mseconds >= interval + 3000) {                       // If time passed - game over
                if (fishBoneX < greenCircleX + greenCircle.getWidth() &&
                        fishBoneX + fishBone.getWidth() > greenCircleX &&
                        fishBoneY < greenCircleY + greenCircle.getHeight() &&
                        fishBone.getHeight() + fishBoneY > greenCircleY) {
                    Intent intent = new Intent(context, GameOverThirdLevel.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            }
            else {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    oldX = event.getX();
                    oldY = event.getY();
                    oldFishBoneX = fishBoneX;
                    oldFishBoneY = fishBoneY;
                }
                if (action == MotionEvent.ACTION_MOVE) {
                    float shiftX = oldX - touchX;
                    float shiftY = oldY - touchY;
                    float newSymptomX = oldFishBoneX - shiftX;
                    float newSymptomY = oldFishBoneY - shiftY;

                    if (newSymptomX <= 0) fishBoneX = 0;
                    else if (newSymptomX >= dWidth - fishBone.getWidth())
                        fishBoneX = dWidth - fishBone.getWidth();
                    else {
                        fishBoneX = (int)newSymptomX;
                        fishBoneY = (int)newSymptomY;

                    }


                }
            }

        }


        return true;
    }
}
