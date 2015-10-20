package com.example.pasha.mybtosc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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

    public void Set_data(int[] data) {
        Bank=null;
        Bank=new int[data.length];
        Bank=data;
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        c.drawColor(Color.WHITE);
        int MyWidth=c.getWidth();
        int MyHeight=c.getHeight();
        int BasikLine=MyHeight-100;
        int step=MyWidth/(Bank.length-1);
        Paint paint=new Paint();
        paint.setColor(Color.BLUE);

        c.drawLine(0, BasikLine, MyWidth, BasikLine, paint);

//        Path mypath = new Path();
//        mypath.moveTo(0,BasikLine-Bank[0]);
//        for (int i=1; i<Bank.length;i++) {
//            mypath.lineTo( (i)* step, (BasikLine - Bank[i]));
//        }
//        mypath.moveTo(MyWidth, BasikLine-Bank[Bank.length - 1]);
//        c.drawPath(mypath, paint);

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
