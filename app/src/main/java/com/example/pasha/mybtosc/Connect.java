package com.example.pasha.mybtosc;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Pasha on 16.10.2015.
 */
public class Connect extends Thread {
    private final static String TAG = Connect.class.getName();
    private final static int READ_BUFFER_SIZE = 64 * 1024;
    private Socket mSocket;
    Handler H;
    int val[];
    public void disconnect() {
        synchronized (this) {
            if(mSocket != null) {
                try {
                    mSocket.close();
                } catch (Exception e) {
                    Log.d(TAG, "close error: " + e);
                }
            }
        }
    }

    Connect(Handler h){
            H=h;
    }

    public void run() {
        try {
            // открываем сокет и коннектимся к localhost:3128
            // получаем сокет сервера

            Log.d(TAG, "connecting");

            mSocket = new Socket("192.168.0.1", 9876);

            Log.d(TAG, "connected");

            InputStream source = mSocket.getInputStream();

            byte readBuffer[] = new byte[READ_BUFFER_SIZE];
            int  readBufferSize = 0;
            while (true) {

                Log.d(TAG, "read");

                int readSize = source.read(readBuffer, readBufferSize, readBuffer.length - readBufferSize);
                if(readSize < 0)
                    break;


                readBufferSize += readSize;
                Log.d(TAG, "parse " + readBufferSize + " bytes");
                int count = parseData(readBuffer, readBufferSize);
                if(count < readBufferSize) {
                    System.arraycopy(readBuffer, count, readBuffer, 0, readBufferSize - count);
                }
                readBufferSize -= count;
            }
        } catch (Exception e) {
            Log.d(TAG, "run error: " + e);
        } // вывод исключений
    }

    public int[] getData() {
        return val;
    }

    public int sendData(String data) {
        synchronized (this) {
            if (mSocket == null){
                Log.d(TAG, "Error send no socet: " + data);
                return 0;
            }

            try {
                data+="\r\n";
                Log.d(TAG, "Get data for send: " + data);
                OutputStream dis = mSocket.getOutputStream();
                PrintWriter output = new PrintWriter(dis);
                output.println(data);
                output.flush();
            } catch (Exception e) {
                Log.d(TAG, "send error: " + e);
            } // вывод исключений
            return data.length();
        }
    }

    Message msg;

    private int parseData(byte[] data, int size) {
        int offset = 0;
        int index = 0;
        boolean isPrevR = false;
        for(byte val: data) {
            if(index >= size)
                return offset;

            if(val == '\n') {
                int lineLength = index - offset - (isPrevR ? 1 : 0);
                String command = new String(data, offset, lineLength);
                Log.d(TAG, "Get data for parse: " + command);
                handleResponse(command);
                offset = index + 1;
            }

            isPrevR = (val == '\r');

            ++index;
        }
        return offset;
    }

    private void handleResponse(String response){
        try {
            if(response.startsWith("+start: ")) {
                Log.d(TAG, "handle: " + response);
                int val = Integer.valueOf(response.substring(8), 10);

                msg = H.obtainMessage(1, val, 0);
                H.sendMessage(msg);
                Log.d(TAG, "handle: start response " + val);
            }

            if(response.startsWith("+adc;")) {
                Log.d(TAG, "handle: " + response);
                int dataStart=response.indexOf(":");
                String data = response.substring(dataStart+1);
                String Val[]=data.split(",");
                int Dval[]=new int[Val.length];
                for (int i=0;i<Val.length;i++) {
                    Dval[i]=Integer.valueOf(Val[i])/10;
                }
                val =new int[Dval.length];
                val=Dval;
                msg = H.obtainMessage(2, Dval[0], 0);
                H.sendMessage(msg);
                Log.d(TAG, "handle: start response " + Dval[0]);
            }

            //TODO ...

        }catch (Exception e) {
                Log.d(TAG, "Handle error: " + e);
            } // вывод исключений
    }
}
