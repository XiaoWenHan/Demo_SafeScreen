package com.xwhcoder.demo_safescreen.activity;

/**
 * Created by 萧文翰 on 2015/5/6.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.Toast;

import com.xwhcoder.demo_safescreen.R;
import com.xwhcoder.demo_safescreen.receiver.ScreenAutoOprReceiver;


public class MainActivity extends Activity {

    private boolean isForeGround;
    private boolean isSafetyMode;
    private boolean needShowSafeScreenAtOnResume;
    public final static int NEED_SHOW_SAFE_SCREEN = 0x01;
    public final static int NEED_HIDE_SAFE_SCREEN = 0x02;

    public static uiHandler handler;

    private IntentFilter intentFilter;
    private ScreenAutoOprReceiver screenAutoOprReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new uiHandler();
        intentFilter = new IntentFilter();
        screenAutoOprReceiver = new ScreenAutoOprReceiver();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(screenAutoOprReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(screenAutoOprReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isForeGround = true;
        if (needShowSafeScreenAtOnResume&&!isSafetyMode) {
            Toast.makeText(MainActivity.this, getResources().getText(R.string.show_unlock_toast), Toast.LENGTH_SHORT).show();
            needShowSafeScreenAtOnResume = false;
            isSafetyMode=true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isForeGround = false;
    }

    public class uiHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NEED_SHOW_SAFE_SCREEN:
                    //需要显示安全界面
                    if (isForeGround&&!isSafetyMode) {
                        Toast.makeText(MainActivity.this, getResources().getText(R.string.show_unlock_toast), Toast.LENGTH_SHORT).show();
                        needShowSafeScreenAtOnResume = false;
                        isSafetyMode=true;
                    } else {
                        needShowSafeScreenAtOnResume = true;
                    }
                    break;
                case NEED_HIDE_SAFE_SCREEN:
                    //隐藏安全界面
                    Toast.makeText(MainActivity.this, getResources().getText(R.string.hide_unlock_toast), Toast.LENGTH_SHORT).show();
                    isSafetyMode=false;
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            handler.sendEmptyMessage(NEED_HIDE_SAFE_SCREEN);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
