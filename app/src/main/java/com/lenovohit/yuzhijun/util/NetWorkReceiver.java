package com.lenovohit.yuzhijun.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by yuzhijun on 2017/2/4.
 */

public class NetWorkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        HttpNetUtil.INSTANCE.setConnected(context);
    }
}
