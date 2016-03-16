package com.xwhcoder.demo_safescreen.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.xwhcoder.demo_safescreen.R;
import com.xwhcoder.demo_safescreen.receiver.LockScreenReceiver;

/**
 * 作者：萧文翰
 * 邮箱：wh1990xiao2005@hotmail.com
 * 移动电话：+86 158-2293-2537
 * 微博：http://weibo.com/xwhnew
 * 个人网站：http://www.xwhcoder.com
 */
public class MainActivity extends AppCompatActivity {

    private CheckBox screenOffCb, backgroundRunningCb;

    private IntentFilter intentFilter;
    private LockScreenReceiver lockScreenReceiver;

    private Toast toast;

    private boolean isNeedSafeCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setListener();
        init();
    }

    //初始化控件
    private void findView() {
        screenOffCb = (CheckBox) findViewById(R.id.mode_screen_off_cb);
        backgroundRunningCb = (CheckBox) findViewById(R.id.mode_background_cb);
    }

    //设置控件监听器
    private void setListener() {
        //黑屏使能
        screenOffCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showToast(getResources().getString(R.string.tip_screen_off_mode_enabled));
                    registerReceiver(lockScreenReceiver, intentFilter);
                } else {
                    showToast(getResources().getString(R.string.tip_screen_off_mode_disabled));
                    unregisterReceiver(lockScreenReceiver);
                    lockScreenReceiver.unRegist();
                }
            }
        });
        //后台运行使能
        backgroundRunningCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showToast(getResources().getString(R.string.tip_background_mode_enabled));
                } else {
                    showToast(getResources().getString(R.string.tip_background_mode_disabled));
                }
            }
        });
    }

    //初始化变量
    private void init() {
        lockScreenReceiver = new LockScreenReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
    }

    //显示Toast
    private void showToast(String str) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (backgroundRunningCb.isChecked()) {
            isNeedSafeCheck = true;
        } else {
            isNeedSafeCheck = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedSafeCheck || lockScreenReceiver.isNeedCheck()) {
            showToast(getResources().getString(R.string.tip_check_show));
        }
    }
}
