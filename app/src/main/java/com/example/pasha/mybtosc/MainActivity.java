package com.example.pasha.mybtosc;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mycon != null) {
                    mycon.disconnect();
                    mycon = null;
                }

                voltage = (TextView) findViewById(R.id.VoltView);


                aHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 1:
                                Integer Volt=msg.arg1;
                                String Val=Volt.toString();
                                voltage.setText(Val);
                                break;
                            case 2:
                                voltage.setText("2048");
                                break;
                            default:
                                voltage.setText("1024");
                                break;
                        }
                    }
                };

                mycon = new Connect(aHandler);
                mycon.start();

                //aHandler.sendEmptyMessage((int)1);
                if (mTimer != null) {mTimer.cancel();}
                mTimer = new Timer();
                mMyTimerTask = new MyTimerTask();
                mTimer.schedule(mMyTimerTask, 1000, 1000);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mycon.sendData("at+start=1");
                }
            });
        }
    }




}
