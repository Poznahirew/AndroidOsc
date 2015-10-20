package com.example.pasha.mybtosc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
/**
 * Created by Pasha on 20.10.2015.
 */
public class Drawtriangles extends View {
    public Drawtriangles(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);

        Paint paint=new Paint();
        float x = 350;
        float y = 250;
        float x1 = 950;
        float y1 = 250;
        float x2 = 950;
        float y2 = 1050;
        paint.setColor(Color.BLUE);
        c.drawLine(x, y, x1, y1, paint);
        c.drawLine(x1, y1, x2, y2, paint);
        c.drawLine(x2, y2, x, y, paint);

    }
}