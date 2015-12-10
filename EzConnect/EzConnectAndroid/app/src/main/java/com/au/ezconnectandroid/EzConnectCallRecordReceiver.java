package com.au.ezconnectandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

/**
 * EzConnect Created by Lakhpat on 10/5/2015.
 */
public class EzConnectCallRecordReceiver extends BroadcastReceiver {

    private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".mp3";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";

    private final int RECORDING_DELAY = 10000;

    static public MediaRecorder recorder = null;
    static String fName = null;
    static boolean status = false;
    static int path = 0;
    static public AudioManager audioManager = null;
    private int currentFormat = 1;
    private int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4,
            MediaRecorder.OutputFormat.THREE_GPP };
    private String file_exts[] = { AUDIO_RECORDER_FILE_EXT_MP4,
            AUDIO_RECORDER_FILE_EXT_3GP };

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();

        audioManager = (AudioManager)context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

        if(status==false)
        {
            status=true;
            final Handler handler = new Handler();
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                public void run() {
                    handler.post(new Runnable() {
                        public void run() {
                            startRecording();
                        }
                    });
                }
            }, RECORDING_DELAY);

        }

    }

    private void startRecording(){

        audioManager.setMode(AudioManager.MODE_IN_CALL);

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
        recorder.setOutputFile(getFilename());

        try {
            //callManager.setMute(true);
            recorder.prepare();
            recorder.start();

        } catch (IllegalStateException e) {
            Log.e("RECORDING :: ", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("RECORDING :: ", e.getMessage());
            e.printStackTrace();
        }
    }

    private String getFilename() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);

        if (!file.exists()) {
            file.mkdirs();
        }

        fName = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + file_exts[currentFormat]);
        return fName;
    }

}
