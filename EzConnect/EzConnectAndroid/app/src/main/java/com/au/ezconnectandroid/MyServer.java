package com.au.ezconnectandroid;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class MyServer {
    Thread m_objThread;
    ServerSocket m_server;
    String m_strMessage;
    DataDisplay m_dataDisplay;
    Object m_connected;
    private String TAG="MyServer";

    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message status){
            Log.d("MyServer","handle message");
            m_dataDisplay.Display(status.obj.toString());
        }
    };
    private int PORT_NO = 1543;
    public MyServer()
    {

    }

    public void setEventListner(DataDisplay dataDisplay)
    {
        m_dataDisplay=dataDisplay;
    }

    public void startListening()
    {
        m_objThread=new Thread(new Runnable(){
            public void run()
            {
                try{
                    m_server=new ServerSocket(PORT_NO);
                    Socket connectedSocket=m_server.accept();
                    Message clientmessage=Message.obtain();
                    BufferedReader output= new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));

                    Log.d("MyServer","listening");
                    String strMessage=(String) output.readLine();
                    clientmessage.obj=strMessage;
                    mHandler.sendMessage(clientmessage);
                    Log.d("MyServer","sendMessage");
                    m_server.close();
                    startListening();
                }
                catch(Exception e){
                    Log.e(TAG, "Connection error");
                }
            }});
        m_objThread.start();
    }
}

