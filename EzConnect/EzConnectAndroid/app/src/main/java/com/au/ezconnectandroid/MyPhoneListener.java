package com.au.ezconnectandroid;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.CallLog;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.internal.telephony.CallManager;
import com.android.internal.telephony.ITelephony;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by Lakhpat on 9/23/2015.
 */
public class MyPhoneListener extends PhoneStateListener {

    public final static String EXTRA_MESSAGE = "com.au.ezconnectandroid.MESSAGE";
    static MainActivity context;
    AudioManager audioManager;
    TelephonyManager telephonyManager;
    String fName = null;
    private boolean onCall = false;

    public MyPhoneListener(MainActivity _context,TextView _callLog, TelephonyManager _telephonyManager){
        context = _context;
        telephonyManager = _telephonyManager;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                // phone ringing...
                Toast.makeText(context.getApplicationContext(), incomingNumber + " calls you",
                        Toast.LENGTH_LONG).show();

                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                // one call exists that is dialing, active, or on hold
                Toast.makeText(context.getApplicationContext(), "on call...",
                        Toast.LENGTH_LONG).show();

                onCall = true;
                context.currentStatus = context.PHONE_ON_CALL;
                EzConnectCallRecordReceiver.audioManager.setSpeakerphoneOn(true);

                break;
            case TelephonyManager.CALL_STATE_IDLE:
                // in initialization of the class and at the end of phone call
                // detect flag from CALL_STATE_OFFHOOK
                if (onCall == true) {
                    Toast.makeText(context.getApplicationContext(), "app after call",
                            Toast.LENGTH_LONG).show();

                    onCall = false;

                    try{
                        if (null != EzConnectCallRecordReceiver.recorder) {
                            EzConnectCallRecordReceiver.recorder.stop();
                            EzConnectCallRecordReceiver.audioManager.setSpeakerphoneOn(false);
                            EzConnectCallRecordReceiver.recorder.reset();
                            EzConnectCallRecordReceiver.recorder.release();
                            EzConnectCallRecordReceiver.recorder = null;
                            EzConnectCallRecordReceiver.status=false;
                        }
                    }catch(RuntimeException stopException){

                    }
                    context.currentStatus = context.PHONE_IDLE;
                    context.lastCallLog();

                }
                break;
            default:
                break;
        }
    }

    public List<String> getLastCallDetails(int required){
        List<String> cData = new ArrayList<String>();
        String detail = "";
        String strOrder = android.provider.CallLog.Calls.DATE + " DESC";

        /* Query the CallLog Content Provider */
        Cursor managedCursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, strOrder);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);

        while (managedCursor.moveToNext()) {
            String phNum = managedCursor.getString(number);
            String callTypeCode = managedCursor.getString(type);
            String strcallDate = managedCursor.getString(date);
            Date callDate = new Date(Long.valueOf(strcallDate));
            String callDuration = managedCursor.getString(duration);
            String callType = null;
            int callcode = Integer.parseInt(callTypeCode);

            switch (callcode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    callType = "Outgoing";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    callType = "Incoming";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    callType = "Missed";
                    break;
            }

            cData.add(0,callDate.toString());
            cData.add(1,callDuration);

            if(required==1)
                break;
        }
        managedCursor.close();
        return cData;
    }

}
