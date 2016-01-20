package com.svs.myprojects.serviceproject;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by snehalsutar on 1/20/16.
 */
public class ServiceClass extends Service {

    MediaPlayer mp;
    TextView text_music_status;
//    Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        mp = MediaPlayer.create(this, R.raw.music);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
        mp.start();
//        text_music_status.setText("Music is playing");


        // processing done here….
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(Constants.ACTION_MUSIC_START);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(Constants.PARAM_OUT_MSG_FROMSERVICE_MSTART, "Music is playing");
        sendBroadcast(broadcastIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "onBind", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
        mp.stop();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(Constants.ACTION_MUSIC_STOP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(Constants.PARAM_OUT_MSG_FROMSERVICE_MSTOP, "Music stopped");
        sendBroadcast(broadcastIntent);
//        text_music_status.setText("Music stopped");
    }
}
