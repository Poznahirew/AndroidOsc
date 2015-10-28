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
    private int Dank[];
    private int avg,ptp=0;

    public OscGraph(Context context) {
        super(context);
    }

    public OscGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OscGraph(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void Set_data(int[] I,int[] Q) {
        Bank=I;
        Dank=Q;
        int min=4095,max=0,count=0;

        for (int item:Bank
             ) {
            if (item>max)max=item;
            if (item<min)min=item;
        }
        ptp=max-min;
        ptp=ptp*10;
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
        float Height=(float)MyHeight;
        float Kmult=(float)401/Height;
        int BasikLine=MyHeight-40;
        int step=MyWidth/(Bank.length-1);
        Paint paint=new Paint();
        paint.setColor(Color.BLUE);
        c.drawLine(0, BasikLine, MyWidth, BasikLine, paint);

        c.drawText("PtP=" + ptp, 5, BasikLine + 20, paint);
        c.drawText("AVG="+3,5,BasikLine+40,paint);


       paint.setStrokeWidth(5);

       for (int i=1; i<Bank.length;i++) {
           int  startX, startY, stopX, stopY;
           startX=(i-1)*step;
           float temp=Bank[(i-1)]/Kmult;
           startY=BasikLine-(int)temp;
           temp=Bank[(i)]/Kmult;
           stopX=i*step;
           stopY=BasikLine-(int)temp;
           c.drawLine(startX, startY, stopX, stopY,paint);//.lineTo((i) * step, (BasikLine - Bank[i]));
        }

        paint.setStrokeWidth(5);
        paint.setColor(Color.GREEN);

        for (int i=1; i<Dank.length;i++) {
            int  startX, startY, stopX, stopY;
            startX=(i-1)*step;
            float temp=Dank[(i-1)]/Kmult;
            startY=BasikLine-(int)temp;
            temp=Dank[(i)]/Kmult;
            stopX=i*step;
            stopY=BasikLine-(int)temp;
            c.drawLine(startX, startY, stopX, stopY,paint);//.lineTo((i) * step, (BasikLine - Bank[i]));
        }

    }
}
