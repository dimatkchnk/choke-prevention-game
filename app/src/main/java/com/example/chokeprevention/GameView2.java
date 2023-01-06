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

    static int goodChoices = 0; // Variable to determine if user separate symptoms in right way

    ArrayList<SymptomRectangle> rectangles = new ArrayList<>();
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
            SymptomRectangle rectangle = new SymptomRectangle(context, dWidth/8f + d, 1.55f * dHeight/2);
            rectangles.add(rectangle);
            d += rectangle.getRectangleWidth();
        }

        // Define symptoms
        for (int i = 0; i < 6; i++) {
            Symptom symptom = new Symptom(context);
            symptoms.add(symptom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(background, null, rectBackground, null);

        // Draw rectangles
        for (SymptomRectangle rectangle : rectangles) {
            canvas.drawBitmap(rectangle.getRectangle(), rectangle.rectangleX, rectangle.rectangleY, null);
        }


        // Symptoms loop to check position of symptoms and draw them
        for (int i = 0; i < symptoms.size(); i++) {
                canvas.drawBitmap(symptoms.get(i).getSymptom(), symptoms.get(i).symptomX, symptoms.get(i).symptomY, null);
            for (SymptomRectangle rectangle : rectangles) {

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

                        symptoms.get(i).symptomX = (int)rectangle.rectangleX + 50; // Put symptom in rectangle
                        symptoms.get(i).symptomY = (int)rectangle.rectangleY + 10;
                        rectangle.filled = true;
                        symptoms.get(i).movable = false; // Unable to move symptom when it's already in rectangle
                        if (goodChoices == 3) {
                            Intent intent = new Intent(context, LevelPassed.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        } else if (goodChoices != 3 && filledRectangles == 3) {
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
        touched = false;
        return true;
    }
}


















