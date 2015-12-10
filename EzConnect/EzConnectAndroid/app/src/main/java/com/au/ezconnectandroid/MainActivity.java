/**
 * EzConnect Created by Lakhpat on 10/7/2015.
 */

package com.au.ezconnectandroid;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.Uri;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.View;
import android.content.Intent;
import com.au.connect.StateReport;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements DataDisplay{
    public final static String EXTRA_MESSAGE = "com.au.ezconnectandroid.MESSAGE";
    final static int PHONE_IDLE = 0;
    final static int SMS_SENT = 1;
    final static int PHONE_ON_CALL = 2;
    private static final String TAG="MainActivity";
    public static int currentStatus = 0;
    static  TelephonyManager telephonyManager=null;
    static MyPhoneListener phoneListener=null;
    final int PICK_CONTACT = 1;
    private final int CALL_DELAY = 10000;
    private final int LOG_DATA_DELAY = 3000;
    private final int RESTART_DELAY = 1000;
    private final int DELIVERED_DELAY = 45000;
    public TextView callLog = null;

    Context context = MainActivity.this;
    static boolean isDelivered = false;
    String callId=null;
    String callee1=null;
    String callee2=null;
    StateReport stateReport;
    MyServer server;
    private EditText phone1;
    private EditText phone2;
    private TextView callDetails;

    int sentStatusFlag;
    int deliveryStatusFlag;
    int callStatusFlag;
    int finalStatusFlag;

    public static int getCurrStatus(){
        return currentStatus;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phone1 = (EditText) findViewById(R.id.phone1);
        phone2 = (EditText) findViewById(R.id.phone2);
        callDetails = (TextView) findViewById(R.id.callDetails);

        stateReport=new StateReport();

        server=new MyServer();
        server.setEventListner(this);
        server.startListening();

        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        phoneListener = new MyPhoneListener(this,callLog,telephonyManager);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);


        if (android.os.Build.VERSION.SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Log.d(TAG, "Starting network call");

    }

    public void Display(String message)
    {

        switch (getCurrStatus()) {

            case PHONE_IDLE:
                StringTokenizer st = new StringTokenizer(message);
                Log.d(TAG, "message data: " + message);
                callId = st.nextToken();
                callee1 = st.nextToken();
                callee2 = st.nextToken();
                phone1.setText(callee1);
                phone2.setText(callee2);
                callDetails.setText(" ");

                Log.d(TAG, "Interviewer number: " + callee1);

                sentStatusFlag=0;
                deliveryStatusFlag=0;
                callStatusFlag=0;
                finalStatusFlag=0;

                Toast.makeText(getBaseContext(), "In Active mode",
                        Toast.LENGTH_SHORT).show();
                stateReport.sendStatus(callId, CallStateEnum.CONNECTED.name());

                isDelivered=false;

                sendSms(callee1, callee2);
                break;

            case SMS_SENT:
            case PHONE_ON_CALL:
                stateReport.sendStatus(callId, CallStateEnum.CALL_BUSY.name());
                break;
        }
    }

    public void sendSms(String interviewer,String callee2 ){

        String smsData = "Dear Interviewer, An Interview is scheduled for you. You will shortly get" +
                " a call from Accolite. Please connect conference call with Interviewee no. "+callee2;
        final String phoneNumber = callee1;

        Log.d(TAG, "In sendSms function " + callee1);

        String  SENT = "SMS_SENT";
        String  DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                if (getResultCode() == Activity.RESULT_OK) {
                    if (sentStatusFlag==0) {
                        stateReport.sendStatus(callId, CallStateEnum.SMS_SENT.name());
                        sentStatusFlag = 1;
                    }
                    currentStatus = SMS_SENT;
                    Log.d(TAG, "SMS sent ");
                    Toast.makeText(getBaseContext(), "SMS sent",
                            Toast.LENGTH_SHORT).show();
                    final Handler handler = new Handler();
                    Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        public void run() {
                            handler.post(new Runnable() {
                                public void run() {

                                    if (!isDelivered) {
                                        Log.d(TAG, "SMS not delivered ");
                                        Toast.makeText(getBaseContext(), "SMS not delivered on time",
                                                Toast.LENGTH_SHORT).show();
                                        currentStatus = PHONE_IDLE;
                                        resetPage();
                                        if(deliveryStatusFlag==0){
                                            stateReport.sendStatus(callId, CallStateEnum.SMS_NOT_DELIVERED.name());
                                            deliveryStatusFlag=1;
                                        }
                                    }
                                    else{
                                        Log.d(TAG, "SMS delivered");

                                        Toast.makeText(getBaseContext(), "SMS delivered",
                                                Toast.LENGTH_SHORT).show();
                                        isDelivered(callee1);
                                    }
                                }
                            });
                        }
                    }, DELIVERED_DELAY);
                } else if (getResultCode() == SmsManager.RESULT_ERROR_GENERIC_FAILURE) {
                    Toast.makeText(getBaseContext(), "SMS not sent",
                            Toast.LENGTH_SHORT).show();
                    currentStatus = PHONE_IDLE;
                    resetPage();
                    if (sentStatusFlag==0) {
                        stateReport.sendStatus(callId, CallStateEnum.SMS_FAILED.name());
                        sentStatusFlag = 1;
                    }
                } else if (getResultCode() == SmsManager.RESULT_ERROR_NO_SERVICE) {
                    Toast.makeText(getBaseContext(), "SMS not sent",
                            Toast.LENGTH_SHORT).show();
                    currentStatus = PHONE_IDLE;
                    resetPage();
                    if (sentStatusFlag==0) {
                        stateReport.sendStatus(callId, CallStateEnum.SMS_FAILED.name());
                        sentStatusFlag = 1;
                    }
                } else if (getResultCode() == SmsManager.RESULT_ERROR_RADIO_OFF) {
                    Toast.makeText(getBaseContext(), "SMS not sent",
                            Toast.LENGTH_SHORT).show();
                    currentStatus = PHONE_IDLE;
                    resetPage();
                    if (sentStatusFlag==0) {
                        stateReport.sendStatus(callId, CallStateEnum.SMS_FAILED.name());
                        sentStatusFlag = 1;
                    }
                } else {
                    Toast.makeText(getBaseContext(), "SMS not sent",
                            Toast.LENGTH_SHORT).show();
                    currentStatus = PHONE_IDLE;
                    resetPage();
                    if (sentStatusFlag==0) {
                        stateReport.sendStatus(callId, CallStateEnum.SMS_FAILED.name());
                        sentStatusFlag = 1;
                    }
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                if (getResultCode() == Activity.RESULT_OK) {
                    isDelivered = true;
                } else {
                    Toast.makeText(getBaseContext(), "SMS not delivered",
                            Toast.LENGTH_SHORT).show();
                    resetPage();
                    if (deliveryStatusFlag == 0) {
                        stateReport.sendStatus(callId, CallStateEnum.SMS_NOT_DELIVERED.name());
                        deliveryStatusFlag = 1;
                    }
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, smsData, sentPI, deliveredPI);

    }

    public void isDelivered(String callee1){

        if(deliveryStatusFlag==0) {
            stateReport.sendStatus(callId, CallStateEnum.SMS_DELIVERED.name());
            deliveryStatusFlag = 1;
            placeCall(callee1);
        }

    }

    public void placeCall(String phoneNumber){

        final String pNumber = phoneNumber;
        final Handler handler = new Handler();
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        callHandler(pNumber);
                    }
                });
            }
        }, CALL_DELAY);

    }

    public void callHandler(String phoneNumber){
        if(callStatusFlag==0) {
            stateReport.sendStatus(callId, CallStateEnum.CALL_IN_PROGRESS.name());
            callStatusFlag = 1;
            callInterviewer(phoneNumber);
        }
    }

    public  void callInterviewer(String callee1){
        String uri = "tel:" + callee1;
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
        startActivity(callIntent);

    }


    public void giveDelay(int dTime){
        Runnable r = new Runnable() {
            @Override
            public void run(){
            }
        };
        Handler h = new Handler();
        h.postDelayed(r, dTime);
    }


    public void lastCallLog(){
        Runnable r = new Runnable() {
            @Override
            public void run(){
                List<String> cData = phoneListener.getLastCallDetails(1);
                Log.d(TAG,"call Log data: "+cData.get(0));
                String callTime = cData.get(0);
                String callDuration = cData.get(1);
                String fName = EzConnectCallRecordReceiver.fName;
                File file = new File(fName);
                if(Integer.parseInt(callDuration) == 0) {
                    file.delete();
                    Log.d(TAG, "Call not happened");
                    if(finalStatusFlag==0) {
                        stateReport.sendStatus(callId, CallStateEnum.CALL_FAILED.name());
                        finalStatusFlag = 1;
                    }
                    resetPage();
                }
                else {
                    if(finalStatusFlag==0) {
                        stateReport.sendStatus(callId, CallStateEnum.CALL_ENDED.name());
                        finalStatusFlag = 1;
                    }

                    stateReport.sendCallLog(callId, callDuration, fName);

                    Toast.makeText(context.getApplicationContext(), "file name :" + fName,
                            Toast.LENGTH_LONG).show();
                    Toast.makeText(getBaseContext(), callTime + " " + callDuration,
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Call sent");
                    Log.d(TAG, "file name :" + fName);
                    Log.d(TAG, callTime + " " + callDuration);
                    callDetails.setText("Last call details:\n" + "Call Time : " + callTime + "\nCall Duration : " + callDuration);
                    resetPage();

                }

            }
        };

        Handler h = new Handler();
        h.postDelayed(r, LOG_DATA_DELAY);
    }

    public void resetPage(){
        phone1.setText("");
        phone2.setText("");
    }

    public void restartApp(){
        final Handler handler = new Handler();
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        // restart our application
                        Intent restart = context.getApplicationContext().getPackageManager().
                                getLaunchIntentForPackage(context.getApplicationContext().getPackageName());
                        restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.getApplicationContext().startActivity(restart);
                    }
                });
            }
        }, RESTART_DELAY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if(resultCode == RESULT_OK){
            Log.d(TAG,"in onActivityResult()");
        }
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

}
