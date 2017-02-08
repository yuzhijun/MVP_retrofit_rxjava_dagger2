package com.lenovohit.yuzhijun.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import com.lenovohit.yuzhijun.inject.component.AppComponent;
import com.lenovohit.yuzhijun.util.RxBus;
import com.lenovohit.yuzhijun.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * 基类activity
 * Created by yuzhijun on 2016/4/28.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected CompositeSubscription mCompositeSubscription
            = new CompositeSubscription();

    private LoadingDialog mLoading;

    public static BaseActivity currentActivity;

    protected List<Subscription> mSubscriptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity = this;
        setupComponent(BaseApplication.get(this).component());
        beforeContentView();
        setContentView(getContentView());
        obtainParam(getIntent());
        initView();
        initListener();
        initData();
    }

    public final void showLoading(@StringRes int textResId) {
        showLoading(getString(textResId));
    }

    public final void showLoading(String text) {
        cancelLoading();
        if (mLoading == null) {
            mLoading = new LoadingDialog(this);
            mLoading.setCancelable(true);
            mLoading.setCanceledOnTouchOutside(false);
        }
        mLoading.setTitle(text);
        mLoading.show();
    }

    public final void cancelLoading() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismiss();
        }
    }

    protected final  <T> Subscription registerEventCallBack(Class<T> event){
         Subscription subscription = RxBus.getDefault().toObservable(event)
                .subscribe(new Action1<T>() {
                    @Override
                    public void call(T t) {
                            rxbusCallBack(t);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        cancelLoading();
                    }
                });
        return subscription;
    }

    protected abstract void obtainParam(Intent intent);

    protected abstract void beforeContentView();

    protected abstract int getContentView();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    protected abstract void setupComponent(AppComponent appComponent);

    protected abstract <T> void rxbusCallBack(T t);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        mCompositeSubscription.unsubscribe();
        if (mSubscriptions != null && mSubscriptions.size() > 0){
            for (Subscription subscription : mSubscriptions){
                if (!subscription.isUnsubscribed()){
                    subscription.unsubscribe();
                }
            }
        }
    }
}
