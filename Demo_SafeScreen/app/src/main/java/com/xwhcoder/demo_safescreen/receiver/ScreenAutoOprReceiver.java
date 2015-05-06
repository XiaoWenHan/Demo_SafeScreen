package com.xwhcoder.demo_safescreen.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xwhcoder.demo_safescreen.activity.MainActivity;

/**
 * Created by 萧文翰 on 2015/5/6.
 */
public class ScreenAutoOprReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == Intent.ACTION_SCREEN_ON) {
            Log.d("demo_safescreen", "The screen has been closed");
            if (MainActivity.handler != null) {
                MainActivity.handler.sendEmptyMessage(MainActivity.NEED_SHOW_SAFE_SCREEN);
            }
        }
    }

}
