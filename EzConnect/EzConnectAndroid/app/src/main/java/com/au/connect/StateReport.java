
/**
 * EzConnect Created by Lakhpat on 10/7/2015.
 */

package com.au.connect;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.au.ezconnectandroid.MainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class StateReport {

    Context context;

    public StateReport(){
    }

    public StateReport(Context sp){
        context = sp;
    }

    private String TAG="taging: STATE Report";
    int flag=0;

    public void sendDeviceIp(String imei,String ip){

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("clientImei", imei);
        params.put("clientIp",ip);
        params.put("clientPort","1543");
        client.post(EzConnectServerIP.URL_IP_ACCEPT, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.i(TAG, " connection data sent");
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i(TAG, "Connection data failed");
                EzConnectServerIP.URL_IP_ACCEPT = "/EzConnectPortal/ipReceive";
                EzConnectServerIP.URL_DATA = "/EzConnectPortal/callData";
                EzConnectServerIP.URL_STATUS ="/EzConnectPortal/stateReceive";
            }
        });

    }


    public void sendStatus(String uUID,String state){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("uuid",uUID );
        params.put("state",state);
        client.post(EzConnectServerIP.URL_STATUS, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.i(TAG, "Status send Connection Successfull");
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i(TAG,"Status send Connection failed");
            }
        });

    }


    public void sendCallLog(String uUID, String callDuration, String fName)  {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("uuid",uUID );
        params.put("callDuration",callDuration);

        InputStream myFile = null;
        try {
            myFile = new FileInputStream(new File(fName));
        } catch (FileNotFoundException e) {
            Log.e(TAG, "No file exists");
        }
        Log.d(TAG, "Data" + myFile);
        params.put("recording", myFile);
        client.post(EzConnectServerIP.URL_DATA, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.i(TAG, "Data send Connection Successful");

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i(TAG, "Data send Connection failed");
            }
        });


    }


}

