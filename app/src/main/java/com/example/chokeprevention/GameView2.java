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

import java.util.ArrayList;

public class GameView2 extends View {

    private Bitmap background;
    private Bitmap label;

    Rect rectBackground;
    Context context;
    Handler handler;
    Runnable runnable;
    private final long UPDATE_MILLIS = 30;
    static int dWidth, dHeight;
    private ArrayList<Symptom> symptoms = new ArrayList<>();
    float oldX, oldY;
    float oldSymptomX, oldSymptomY;
    boolean touched = false;

    static int goodChoices = 0; // Variable to determine if user doing all in right way

    ArrayList<SymptomRectangle> bigRectangles = new ArrayList<>();
    ArrayList<SmallRectangle> smallRectangles = new ArrayList<>();
    ArrayList<Letter> letters = new ArrayList<>();

    int filledRectangles = 0;

    public GameView2(Context context) {
        super(context);
        this.context = context;

        float d = 0;

        background = BitmapFactory.decodeResource(getResources(), R.drawable.second_background);
        label = BitmapFactory.decodeResource(getResources(), R.drawable.level2label);


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

        // Define rectangles
        for (int i = 0; i < 3; i++) {
            SymptomRectangle rectangle = new SymptomRectangle(context, dWidth/12f + d, 1.55f * dHeight/2);
            bigRectangles.add(rectangle);
            d += rectangle.getRectangleWidth();
        }
        for (int i = 0; i < 4; i++) {
            SmallRectangle rectangle = new SmallRectangle(context, -600 + d, .2f * dHeight/2);
            smallRectangles.add(rectangle);
            d += rectangle.getRectangleWidth();
        }


        // Define symptoms
        for (int i = 0; i < 6; i++) {
            Symptom symptom = new Symptom(context);
            symptoms.add(symptom);
        }

        //Define letters
        for (int i = 0; i < 4; i++) {
            Letter letter = new Letter(context);
            letters.add(letter);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(background, null, rectBackground, null);

        // Draw rectangles
        for (SymptomRectangle rectangle : bigRectangles) {
            canvas.drawBitmap(rectangle.getRectangle(), rectangle.rectangleX, rectangle.rectangleY, null);
        }
        for (SmallRectangle rectangle : smallRectangles) {
            canvas.drawBitmap(rectangle.getRectangle(), rectangle.rectangleX, rectangle.rectangleY, null);
        }

        for (Letter letter : letters) {
            canvas.drawBitmap(letter.getLetter(), letter.letterX, letter.letterY, null);

            for (SmallRectangle rectangle : smallRectangles) {

                // Collision detection between letter and small rectangle
                if (letter.letterX < rectangle.rectangleX + rectangle.getRectangleWidth() &&
                        letter.letterX + letter.getLetterWidth() > rectangle.rectangleX &&
                        letter.letterY < rectangle.rectangleY + rectangle.getRectangleHeight() &&
                        letter.getLetterHeight() + letter.letterY > rectangle.rectangleY)

                {


                    // Check if rectangle already has letter inside
                    if (!rectangle.filled) {

                        if (letter.whichLetter.equals("h") && rectangle.pos == 0 ||
                                letter.whichLetter.equals("e") && rectangle.pos == 1 ||
                                letter.whichLetter.equals("l") && rectangle.pos == 2 ||
                                letter.whichLetter.equals("p") && rectangle.pos == 3) {

                            goodChoices++;
                            filledRectangles++;

                            letter.letterX = (int)rectangle.rectangleX + 45; // Put letter in rectangle
                            letter.letterY = (int)rectangle.rectangleY + 45;

                            rectangle.filled = true;
                            letter.movable = false; // Unable to move letter when it's already in rectangle
                        }

                        if (goodChoices == 7) {
                            Intent intent = new Intent(context, LevelPassed.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        } else if (goodChoices != 7 && filledRectangles == 7) {
                            Intent intent = new Intent(context, GameOver.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }
                    }

                }

            }
        }

        // Symptoms loop to check position of symptoms and draw them
        for (int i = 0; i < symptoms.size(); i++) {
                canvas.drawBitmap(symptoms.get(i).getSymptom(), symptoms.get(i).symptomX, symptoms.get(i).symptomY, null);
            for (SymptomRectangle rectangle : bigRectangles) {

                // Collision detection between symptom and rectangle
                if (symptoms.get(i).symptomX < rectangle.rectangleX + rectangle.getRectangleWidth() &&
                        symptoms.get(i).symptomX + symptoms.get(i).getSymptomWidth() > rectangle.rectangleX &&
                        symptoms.get(i).symptomY < rectangle.rectangleY + rectangle.getRectangleHeight() &&
                        symptoms.get(i).getSymptomHeight() + symptoms.get(i).symptomY > rectangle.rectangleY)

                {


                    // Check if rectangle already has symptom inside
                    if (!rectangle.filled) {
                        if (symptoms.get(i).isChokeSymptom) goodChoices++;
                        if (!symptoms.get(i).isChokeSymptom) goodChoices--;


                        filledRectangles++;

                        symptoms.get(i).symptomX = (int)rectangle.rectangleX + 60; // Put symptom in rectangle
                        symptoms.get(i).symptomY = (int)rectangle.rectangleY + 10;
                        rectangle.filled = true;
                        symptoms.get(i).movable = false; // Unable to move symptom when it's already in rectangle
                        if (goodChoices == 7) {
                            Intent intent = new Intent(context, LevelPassed.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        } else if (goodChoices != 7 && filledRectangles == 7) {
                            Intent intent = new Intent(context, GameOver.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }
                    }

                }

            }

        }
        canvas.drawBitmap(label, (float)(dWidth - label.getWidth() - 20), 100, null); // Draw label with text

        handler.postDelayed(runnable, UPDATE_MILLIS); // Run game loop

    }



    // Function that allows moving symptoms
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();

        for (int i = 0; i < symptoms.size(); i++) {
            if (touchX > symptoms.get(i).symptomX && touchX < symptoms.get(i).symptomX + symptoms.get(i).getSymptomWidth()
                && symptoms.get(i).symptomY < touchY && touchY < symptoms.get(i).symptomY + symptoms.get(i).getSymptomHeight() && symptoms.get(i).movable && !touched) {
                touched = true;

                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    oldX = event.getX();
                    oldY = event.getY();
                    oldSymptomX = symptoms.get(i).symptomX;
                    oldSymptomY = symptoms.get(i).symptomY;
                }
                if (action == MotionEvent.ACTION_MOVE) {
                    float shiftX = oldX - touchX;
                    float shiftY = oldY - touchY;
                    float newSymptomX = oldSymptomX - shiftX;
                    float newSymptomY = oldSymptomY - shiftY;

                    if (newSymptomX <= 0) symptoms.get(i).symptomX = 0;
                    else if (newSymptomX >= dWidth - symptoms.get(i).getSymptomWidth())
                        symptoms.get(i).symptomX = dWidth - symptoms.get(i).getSymptomWidth();
                    else {
                        symptoms.get(i).symptomX = (int)newSymptomX;
                        symptoms.get(i).symptomY = (int)newSymptomY;
                    }

                }


            }
        }

        for (Letter letter : letters) {
            if (touchX > letter.letterX && touchX < letter.letterX + letter.getLetterWidth()
                    && letter.letterY < touchY && touchY < letter.letterY + letter.getLetterHeight() && letter.movable && !touched) {
                touched = true;


                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    oldX = event.getX();
                    oldY = event.getY();
                    oldSymptomX = letter.letterX;
                    oldSymptomY = letter.letterY;
                }
                if (action == MotionEvent.ACTION_MOVE) {
                    float shiftX = oldX - touchX;
                    float shiftY = oldY - touchY;
                    float newSymptomX = oldSymptomX - shiftX;
                    float newSymptomY = oldSymptomY - shiftY;

                    if (newSymptomX <= 0) letter.letterX = 0;
                    else if (newSymptomX >= dWidth - letter.getLetterWidth())
                        letter.letterX = dWidth - letter.getLetterWidth();
                    else {
                        letter.letterX = (int)newSymptomX;
                        letter.letterY = (int)newSymptomY;
                    }

                }


            }
        }
        touched = false;
        return true;
    }
}


















