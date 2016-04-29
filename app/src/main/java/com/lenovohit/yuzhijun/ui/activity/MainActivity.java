package com.lenovohit.yuzhijun.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.lenovohit.yuzhijun.R;
import com.lenovohit.yuzhijun.base.BaseSubscriber;
import com.lenovohit.yuzhijun.inject.component.AppComponent;
import com.lenovohit.yuzhijun.inject.component.DaggerActivityComponent;
import com.lenovohit.yuzhijun.inject.module.ActivityModule;
import com.lenovohit.yuzhijun.model.Weather2;
import com.lenovohit.yuzhijun.network.SubscriberListener;
import com.lenovohit.yuzhijun.ui.presenter.MainActivityPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by yuzhijun on 2016/4/28.
 */
public class MainActivity extends SimpleActivity{


    @Bind(R.id.tvShow)
    TextView tvShow;

    @Inject
    MainActivityPresenter mainActivityPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        Subscription subscription =  mainActivityPresenter.getWeatherData();
        mCompositeSubscription.add(subscription);
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onNext(Object o, int flag) {
        switch (flag) {
            case 1:
                tvShow.setText("ddddd");
                break;
            default:
                break;
        }
    }
}
