package com.lenovohit.yuzhijun.util;

import android.widget.Toast;

import com.lenovohit.yuzhijun.base.BaseActivity;

/**
 * Created by yuzhijun on 2017/2/8.
 */

public class ViewUtil {
    public static void showToast(String showStr, boolean isLong) {
        if(BaseActivity.currentActivity != null) {
            int var2 = Toast.LENGTH_SHORT;
            if(isLong) {
                var2 = Toast.LENGTH_LONG;
            }
            Toast.makeText(BaseActivity.currentActivity, showStr, var2).show();
        }
    }
}
