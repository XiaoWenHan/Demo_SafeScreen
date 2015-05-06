package com.xwhcoder.demo_safescreen.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xwhcoder.demo_safescreen.activity.MainActivity;

/**
 * Created by 萧文翰 on 2015/5/6.
 */
public class UserUnlockReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == Intent.ACTION_USER_PRESENT) {
            Log.d("demo_safescreen", "The screen has been unlocked (system)");
            if (MainActivity.handler != null) {
                MainActivity.handler.sendEmptyMessage(MainActivity.NEED_SHOW_SAFE_SCREEN);
            }
        }
    }
}
