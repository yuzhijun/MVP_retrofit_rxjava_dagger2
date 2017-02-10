package com.lenovohit.yuzhijun.ui.activity;

import android.content.Intent;

import com.lenovohit.yuzhijun.base.BaseActivity;

/**
 * 空实现，为了适配继承类，因为有的方法不需要实现
 * 子类根据需要进行实现
 * Created by yuzhijun on 2016/4/28.
 */
public abstract class SimpleActivity extends BaseActivity{

    @Override
    protected void beforeContentView() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void obtainParam(Intent intent) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
