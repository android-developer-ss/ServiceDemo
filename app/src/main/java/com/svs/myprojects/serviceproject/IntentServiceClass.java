package com.svs.myprojects.serviceproject;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by snehalsutar on 1/20/16.
 */
public class IntentServiceClass extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public IntentServiceClass() {
        super("MyServiceThread");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SystemClock.sleep(1500);
//            this.wait(3000);

        // processing done here….
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(Constants.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(Constants.PARAM_OUT_MSG, "Message From Intent Service");
        sendBroadcast(broadcastIntent);
    }
}
