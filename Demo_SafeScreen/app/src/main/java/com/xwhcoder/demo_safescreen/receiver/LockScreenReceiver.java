package com.xwhcoder.demo_safescreen.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 作者：萧文翰
 * 邮箱：wh1990xiao2005@hotmail.com
 * 移动电话：+86 158-2293-2537
 * 微博：http://weibo.com/xwhnew
 * 个人网站：http://www.xwhcoder.com
 */
public class LockScreenReceiver extends BroadcastReceiver {

    private boolean isNeedCheck;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            //关闭屏幕
            isNeedCheck = true;
        }
    }

    public void unRegist() {
        isNeedCheck = false;
    }

    public boolean isNeedCheck() {
        return isNeedCheck;
    }


}
