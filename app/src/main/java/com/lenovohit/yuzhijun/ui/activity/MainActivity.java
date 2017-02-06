package com.lenovohit.yuzhijun.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lenovohit.yuzhijun.R;
import com.lenovohit.yuzhijun.inject.component.AppComponent;
import com.lenovohit.yuzhijun.inject.component.DaggerActivityComponent;
import com.lenovohit.yuzhijun.inject.module.ActivityModule;
import com.lenovohit.yuzhijun.model.Weather2;
import com.lenovohit.yuzhijun.ui.presenter.WeatherPresenter;
import com.lenovohit.yuzhijun.util.RxBus;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by yuzhijun on 2016/4/28.
 */
public class MainActivity extends SimpleActivity{

    @Bind(R.id.tvShow)
    TextView tvShow;

    @Bind(R.id.btnSwitch)
    Button btnSwitch;

    @Inject
    WeatherPresenter mWeatherPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();

        getWeatherData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    public void getWeatherData(){
        mSubscription = RxBus.getDefault().toObservable(Weather2.class)
                .subscribe(new Action1<Weather2>() {
                    @Override
                    public void call(Weather2 weather2) {
                        cancelLoading();
                        tvShow.setText(weather2.getCountry());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        cancelLoading();
                    }
                });
        showLoading(R.string.label_being_something);
        Subscription subscription =  mWeatherPresenter.getWeatherData();
        mCompositeSubscription.add(subscription);
    }
}
