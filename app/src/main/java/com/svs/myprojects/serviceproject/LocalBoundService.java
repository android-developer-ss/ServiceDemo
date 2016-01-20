package com.svs.myprojects.serviceproject;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;

/**
 * Created by snehalsutar on 1/20/16.
 */
public class LocalBoundService extends Service {

    // IBinder is the Interface between Client and Server which returns this service
    private final IBinder mBinder = new LocalBinder();

    private final Random gen = new Random();

    public class LocalBinder extends Binder {
        LocalBoundService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LocalBoundService.this;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // Public methods to be called by clients

    public int getRandomNumber() {
        return gen.nextInt(1000);
    }
}
