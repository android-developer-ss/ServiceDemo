package com.svs.myprojects.serviceproject;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    private ResponseReceiver receiver;
    private ResponseReceiverMusicStart musicStartReceiver;
    private ResponseReceiverMusicStop musicStopReceiver;
    TextView text_music_status;
    TextView text_intent_service_status;
    TextView text_bound_service_status;
    int randomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(MainActivity.this, ServiceClass.class);


        IntentFilter filter = new IntentFilter(Constants.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);

        IntentFilter intentFilterMusicStart = new IntentFilter(Constants.ACTION_MUSIC_START);
        intentFilterMusicStart.addCategory(Intent.CATEGORY_DEFAULT);
        musicStartReceiver = new ResponseReceiverMusicStart();
        registerReceiver(musicStartReceiver, intentFilterMusicStart);

        IntentFilter intentFilterMusicStop = new IntentFilter(Constants.ACTION_MUSIC_STOP);
        intentFilterMusicStop.addCategory(Intent.CATEGORY_DEFAULT);
        musicStopReceiver = new ResponseReceiverMusicStop();
        registerReceiver(musicStopReceiver, intentFilterMusicStop);
    }

    /***********************************************************************************************
     * NORMAL SERVICE
     *
     * @param view
     **********************************************************************************************/
    public void start_button_function(View view) {
        text_music_status = (TextView) findViewById(R.id.text_music_status);
        startService(intent);
    }

    public void stop_button_function(View view) {
        stopService(intent);
    }

    public class ResponseReceiverMusicStart extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra(Constants.PARAM_OUT_MSG_FROMSERVICE_MSTART);
            text_music_status.setText(text);
        }
    }

    public class ResponseReceiverMusicStop extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra(Constants.PARAM_OUT_MSG_FROMSERVICE_MSTOP);
            text_music_status.setText(text);
        }
    }

    /***********************************************************************************************
     * INTENT SERVICE
     *
     * @param view
     **********************************************************************************************/
    public void start_intent_service_function(View view) {

        text_intent_service_status = (TextView) findViewById(R.id.text_intent_service_status);
        String strInputMsg = text_intent_service_status.getText().toString();
        Intent msgIntent = new Intent(this, IntentServiceClass.class);
        msgIntent.putExtra(Constants.PARAM_IN_MSG, strInputMsg);
        startService(msgIntent);
    }

    public void stop_intent_service_function(View view) {
        text_intent_service_status.setText("Intent Service status");
    }


    //----------------------------------------------------------------------------------------------
    // BROADCAST RECEIVER for Intent Service

    public class ResponseReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView result = (TextView) findViewById(R.id.text_intent_service_status);
            String text = intent.getStringExtra(Constants.PARAM_OUT_MSG);
            result.setText(text);
        }
    }


    /***********************************************************************************************
     * BOUND SERVICE
     **********************************************************************************************/

    //----------------------------------------------------------------------------------------------
    // Define a Local Bound Service Connection

    LocalBoundService mService;
    boolean mBound = false;
    //  Defines callbacks for service binding, passed to bindService()
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            LocalBoundService.LocalBinder binder = (LocalBoundService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, LocalBoundService.class);
        // Bind with the bound service with this activity component
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    public void start_bound_service_function(View view) {
        text_bound_service_status = (TextView) findViewById(R.id.text_bound_service_status);
        randomNumber = mService.getRandomNumber();
        text_bound_service_status.setText("Num: " + randomNumber);
    }

    public void stop_bound_service_function(View view) {
        text_bound_service_status.setText("Bound Service status");
    }
}
