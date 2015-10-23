package com.example.pasha.mybtosc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;


import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.example.pasha.mybtosc.Connect;

public class MainActivity extends AppCompatActivity {

    //public static String SERVERIP = "192.168.0.1"; //your computer IP address
    //public static int SERVERPORT = 9876;
    String message;
    String bufer;
    PrintWriter out;
    Connect mycon;
    TextView voltage;
    private Timer mTimer;
    private MyTimerTask mMyTimerTask;

    Handler aHandler;
    int all[];
    SurfaceView Sview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        aHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Integer Volt=msg.arg1;
                        Volt=(Volt*3300)/4095;
                        String Val=Volt.toString();
                        voltage.setText(Val);
                        break;
                    case 2:
                        show_me();
                        voltage.setText("Graph");
                        break;
                    default:
                        voltage.setText("1024");
                        break;
                }
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (mycon != null) {
//                    mycon.disconnect();
//                    mycon = null;
//                }
//
//                voltage = (TextView) findViewById(R.id.VoltView);
//
//                mycon = new Connect(aHandler);
//                mycon.start();
//
//                if (mTimer != null) {mTimer.cancel();}
//                mTimer = new Timer();
//                mMyTimerTask = new MyTimerTask();
//                mTimer.schedule(mMyTimerTask, 1000, 250);

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    OscGraph Osc;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            setContentView(R.layout.activity_settings);
            return true;
        }

        if (id == R.id.exititem) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            return true;
        }

        if (id == R.id.goitem) {

            if (mycon != null) {
                mycon.disconnect();
                mycon = null;
            }

            voltage = (TextView) findViewById(R.id.VoltView);

            mycon = new Connect(aHandler);
            mycon.start();

            if (mTimer != null) {mTimer.cancel();}
            mTimer = new Timer();
            mMyTimerTask = new MyTimerTask();
            mTimer.schedule(mMyTimerTask, 1000, 250);
        }

        return super.onOptionsItemSelected(item);
    }

    public void show_me(){
        OscGraph view = (OscGraph)findViewById(R.id.view);
        view.Set_data(mycon.getData());

//        int a[]=mycon.getData();
//        Osc=new OscGraph(this);
//        Osc.Set_data(a);
//        addContentView(Osc, new LayoutParams(600, 600));
//        addContentView(Osc, findViewById(R.id.layoutOsc).getLayoutParams());
    }

    class MyTimerTask extends TimerTask {
        CheckBox adc=(CheckBox) findViewById(R.id.checkBox);
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (adc.isChecked())
                    mycon.sendData("at+start=20");
                    else
                        mycon.sendData("at+start=1");
                }
            });
        }
    }
}
