/**
 * EzConnect Created by Lakhpat on 10/7/2015.
 */

package com.au.ezconnectandroid;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import  android.content.Context;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.au.connect.EzConnectServerIP;
import com.au.connect.StateReport;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;


public class EzConnectStart extends AppCompatActivity {
    public String TAG="EzConnectStart";
    EditText serverIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        serverIP = (EditText) findViewById(R.id.ipEditText);

        Button serverConnectButton= (Button)findViewById(R.id.submitIP);

        serverConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String imei = tm.getDeviceId();
                WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
                String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

                String serverIpValue=serverIP.getText().toString();

                EzConnectServerIP.URL_IP_ACCEPT = "http://"+serverIpValue+EzConnectServerIP.URL_IP_ACCEPT;
                EzConnectServerIP.URL_DATA = "http://"+serverIpValue+EzConnectServerIP.URL_DATA;
                EzConnectServerIP.URL_STATUS = "http://"+serverIpValue+EzConnectServerIP.URL_STATUS;

                Context context=EzConnectStart.this;
                StateReport sp=new StateReport(context);
                sp.sendDeviceIp(imei, ip);

            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_server_connection, menu);
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
