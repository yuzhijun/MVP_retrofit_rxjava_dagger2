package com.lenovohit.yuzhijun.util;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.lenovohit.yuzhijun.R;
import com.lenovohit.yuzhijun.base.BaseActivity;
import com.lenovohit.yuzhijun.base.BaseApplication;

import java.util.List;

/**
 * Created by yuzhijun on 2017/2/8.
 */

public class ViewUtil {
    /**
     * 弹出吐司
     * */
    public static void showToast(String showStr, boolean isLong) {
        if(BaseActivity.currentActivity != null) {
            int var2 = Toast.LENGTH_SHORT;
            if(isLong) {
                var2 = Toast.LENGTH_LONG;
            }
            Toast.makeText(BaseActivity.currentActivity, showStr, var2).show();
        }
    }

    /**
     * 判断字符串是否为空
     * */
    public static boolean isStrEmpty(String str) {
        return str != null && !"".equals(str.trim())?(str = str.replaceAll(" ", "").trim()) == null || "".equals(str.trim()):true;
    }

    /**
     * 判断对象是否为空
     * */
    public static boolean isObjectEmpty(Object obj){
        return obj == null;
    }

    /**
     * 判断List是否为空或者值为空
     * */
    public static <T> boolean isListEmpty(List<T> list){
       return list == null || list.size() <= 0;
    }

    public static int getResColor(int var0) {
        return BaseApplication.mBaseApplication.getResources().getColor(var0);
    }

    public static String getString(int var0) {
        return BaseApplication.mBaseApplication.getString(var0);
    }

    /**
     * @param key 键
     * @param str 值
     * */
    public static void setShardPString(String key, String str) {
        SharedPreferences.Editor var2;
        (var2 = BaseApplication.mBaseApplication.getSharedPreferences(getString(R.string.app_name), 0).edit()).putString(key, str);
        var2.commit();
    }

    /**
     * @param key 键
     * */
    public static String getShardPStringByKey(String key) {
        return BaseApplication.mBaseApplication.getSharedPreferences(getString(R.string.app_name), 0).getString(key, "");
    }
}
