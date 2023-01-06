package com.example.chokeprevention;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {

    protected  static Bitmap background, mainCharacter;
    Rect rectBackground;
    Context context;
    Handler handler;
    Runnable runnable;
    private final float TEXT_SIZE = 80;
    private final long UPDATE_MILLIS = 30; // How fast update drawings on screen
    Paint textPaint = new Paint();
    Paint healthPaint = new Paint();
    private int points = 90;
    private int life = 3;
    static int dWidth, dHeight; // Screen size
    private Random random;
    protected static float mainCharacterX, mainCharacterY;

    // Variables for TouchEvent function
    private float oldX;
    private float oldY;
    private float oldCharacterX;
    private float oldCharacterY;
    private ArrayList<FoodElement> elements = new ArrayList<>();
    private ArrayList<Effect> effects = new ArrayList<>();


    public GameView(Context context) {
        super(context);
        this.context = context;

        background = BitmapFactory.decodeResource(getResources(), R.drawable.main_background);
        mainCharacter = BitmapFactory.decodeResource(getResources(), R.drawable.main_character2);

        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rectBackground = new Rect(0, 0, dWidth, dHeight);


        // Create and run game loop
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        mainCharacterX = dWidth / 2 - mainCharacter.getWidth() / 2;
        mainCharacterY = dHeight / 2 - mainCharacter.getHeight();


        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        healthPaint.setColor(Color.GREEN);
        for (int i = 0; i < 12; i++) {
            FoodElement element = new FoodElement(context);
            elements.add(element);
        }

    }



    // Draw all bitmaps
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background, null, rectBackground, null);
        canvas.drawBitmap(mainCharacter, mainCharacterX, mainCharacterY, null);

        // Loop for all elements to check their position
        for (int i = 0; i < elements.size(); i++) {
            canvas.drawBitmap(elements.get(i).getElement(), elements.get(i).elementX, elements.get(i).elementY, null );

            // Element movement
            elements.get(i).elementX += elements.get(i).elementSpeedX;
            elements.get(i).elementY += elements.get(i).elementSpeedY;

            // If element reaches screen bounds - change speed to negative
            if (elements.get(i).elementX + elements.get(i).getElementWidth() < i || elements.get(i).elementX + elements.get(i).getElementWidth() >= dWidth) {
                elements.get(i).elementSpeedX = -elements.get(i).elementSpeedX;
            }
            if (elements.get(i).elementY + elements.get(i).getElementHeight() < i || elements.get(i).elementY + elements.get(i).getElementHeight() >= dHeight) {
                elements.get(i).elementSpeedY = -elements.get(i).elementSpeedY;
            }

            // Main character and element collision
            if     (mainCharacterX < elements.get(i).elementX + elements.get(i).getElementWidth() &&
                              mainCharacterX + mainCharacter.getWidth() > elements.get(i).elementX &&
                    mainCharacterY < elements.get(i).elementY + elements.get(i).getElementHeight() &&
                    mainCharacter.getHeight() + mainCharacterY > elements.get(i).elementY) {

                if (elements.get(i).eatable) {
                    points += 10;

                    // Set position of effect
                    Effect effect = new Effect(context);
                    effect.effectX = elements.get(i).elementX;
                    effect.effectY = elements.get(i).elementY;
                    effects.add(effect);

                    // If user reaches 100 points, go to next level
                    if (points == 100) {
                        Intent intent = new Intent(context, LevelPassed.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }

                    // Set a new position for element
                    elements.get(i).setPosition(context);

                } else {
                    life--;
                    elements.get(i).setPosition(context);

                    // If no life left - show game over class or change life bar color
                    if (life == 0) {
                        Intent intent = new Intent(context, GameOver.class);
                        intent.putExtra("points", points);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    } else if (life == 2) {
                        healthPaint.setColor(Color.YELLOW);
                    } else if (life == 1) {
                        healthPaint.setColor(Color.RED);
                    }

                }
            }


        }


        // Loop to draw effect bitmap frames
        for (int i = 0; i < effects.size(); i++) {
            canvas.drawBitmap(effects.get(i).getEffect(effects.get(i).effectFrame), effects.get(i).effectX, effects.get(i).effectY, null);
            effects.get(i).effectFrame++;
            if (effects.get(i).effectFrame > 3) {
                effects.remove(i);
            }
        }

        // Draw life bar and text with current points
        canvas.drawRect(dWidth - 200, 30, dWidth - 200 + 60 * life, 80, healthPaint);
        canvas.drawText("POINTS: " + points, 20, TEXT_SIZE, textPaint);
        handler.postDelayed(runnable, UPDATE_MILLIS); // Run game loop
    }


    // Function that allows moving main character
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        // Clicks on element detection (if 3 clicks - element disappears)
        for (int i = 0; i < elements.size(); i++) {
            if (touchX > elements.get(i).elementX && touchX < elements.get(i).elementX + elements.get(i).getElementWidth() &&
                elements.get(i).elementY < touchY && touchY < elements.get(i).elementY + elements.get(i).getElementHeight()) {
                if (elements.get(i).cutable && elements.get(i).clicks >= 3) {

                    // Set position of effect frames
                    Effect effect = new Effect(context);
                    effect.effectX = elements.get(i).elementX;
                    effect.effectY = elements.get(i).elementY;
                    effects.add(effect);
                    elements.remove(i);
                    for (int j = 0; j < 2; j++) {
                        FoodElement element = new FoodElement(context);
                        elements.add(element);
                    }
                }
                elements.get(i).clicks++; // Count number of clicks on element
            }
        }

        // Touch on main character detection
        if (touchX > mainCharacterX && touchX < mainCharacterX + mainCharacter.getWidth() && mainCharacterY < touchY && touchY < mainCharacterY + mainCharacter.getHeight()) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                oldX = event.getX();
                oldY = event.getY();
                oldCharacterX = mainCharacterX;
                oldCharacterY = mainCharacterY;
            }
            if (action == MotionEvent.ACTION_MOVE) {
                float shiftX = oldX - touchX;
                float shiftY = oldY - touchY;
                float newCharacterX = oldCharacterX - shiftX;
                float newCharacterY = oldCharacterY - shiftY;
                if (newCharacterX <= 0)
                    mainCharacterX = 0;
                else if (newCharacterX >= dWidth - mainCharacter.getWidth()) // Do not let main character get out from screen bounds
                    mainCharacterX = dWidth - mainCharacter.getWidth();
                else {
                    mainCharacterX = newCharacterX;
                    mainCharacterY = newCharacterY;
                }

            }
        }
        return true;
    }
}
