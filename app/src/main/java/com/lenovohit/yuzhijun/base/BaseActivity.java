package com.lenovohit.yuzhijun.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lenovohit.yuzhijun.inject.component.AppComponent;

import rx.subscriptions.CompositeSubscription;

/**
 * 基类activity
 * Created by yuzhijun on 2016/4/28.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected CompositeSubscription mCompositeSubscription
            = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(BaseApplication.get(this).component());
        beforeContentView();
        setContentView(getContentView());
//        SliderUtils.attachActivity(this, null);
        obtainParam(getIntent());
        initView();
        initListener();
        initData();
    }

    protected abstract void obtainParam(Intent intent);

    protected abstract void beforeContentView();

    protected abstract int getContentView();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    protected abstract void setupComponent(AppComponent appComponent);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }
}
