package com.lenovohit.yuzhijun.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import com.lenovohit.yuzhijun.inject.component.AppComponent;
import com.lenovohit.yuzhijun.view.LoadingDialog;

import rx.subscriptions.CompositeSubscription;

/**
 * 基类activity
 * Created by yuzhijun on 2016/4/28.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected CompositeSubscription mCompositeSubscription
            = new CompositeSubscription();

    private LoadingDialog mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(BaseApplication.get(this).component());
        beforeContentView();
        setContentView(getContentView());
        obtainParam(getIntent());
        initView();
        initListener();
        initData();
    }

    protected final void showLoading(@StringRes int textResId) {
        showLoading(getString(textResId));
    }

    protected final void showLoading(String text) {
        cancelLoading();
        if (mLoading == null) {
            mLoading = new LoadingDialog(this);
            mLoading.setCancelable(false);
            mLoading.setCanceledOnTouchOutside(false);
        }
        mLoading.setTitle(text);
        mLoading.show();
    }

    protected final void cancelLoading() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismiss();
        }
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
