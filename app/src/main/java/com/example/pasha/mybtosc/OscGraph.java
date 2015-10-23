package com.example.pasha.mybtosc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.Date;

/**
 * Created by Pasha on 20.10.2015.
 */
public class OscGraph extends View {

    private int Bank[];
    public OscGraph(Context context) {
        super(context);
    }

    public OscGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OscGraph(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void Set_data(int[] data) {
        Bank=data;
        invalidate();
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);

        if(Bank == null)
            return;

        c.drawColor(Color.WHITE);
        int MyWidth=c.getWidth();
        int MyHeight=c.getHeight();
        int BasikLine=MyHeight-100;
        int step=MyWidth/(Bank.length-1);
        Paint paint=new Paint();
        paint.setColor(Color.BLUE);

        c.drawLine(0, BasikLine, MyWidth, BasikLine, paint);

       paint.setStrokeWidth(10);
       for (int i=1; i<Bank.length;i++) {

           int  startX, startY, stopX, stopY;
           startX=(i-1)*step;
           startY=BasikLine-Bank[(i-1)];
           stopX=i*step;
           stopY=BasikLine-Bank[i];
           c.drawLine(startX, startY, stopX, stopY,paint);//.lineTo((i) * step, (BasikLine - Bank[i]));
        }


    }
}
