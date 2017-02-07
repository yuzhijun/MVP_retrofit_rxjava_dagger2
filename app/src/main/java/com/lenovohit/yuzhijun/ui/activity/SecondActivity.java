package com.lenovohit.yuzhijun.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lenovohit.yuzhijun.R;
import com.lenovohit.yuzhijun.inject.component.AppComponent;
import com.lenovohit.yuzhijun.inject.component.DaggerActivityComponent;
import com.lenovohit.yuzhijun.inject.module.ActivityModule;
import com.lenovohit.yuzhijun.model.Weather2;
import com.lenovohit.yuzhijun.model.event.SynchronizedEvent;
import com.lenovohit.yuzhijun.ui.presenter.WeatherPresenter;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by yuzhijun on 2016/5/5.
 */
public class SecondActivity extends SimpleActivity {
    @Bind(R.id.btnSwitch)
    Button btnSwitch;

    @Bind(R.id.tvShow)
    TextView tvShow;

    @Bind(R.id.tvWeatherShow)
    TextView tvWeatherShow;

    @Inject
    WeatherPresenter mWeatherPresenter;

    @Override
    protected int getContentView() {
        return R.layout.content_main;
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

            }
        });
    }

    @Override
    protected <T> void rxbusCallBack(T t) {
        cancelLoading();
        if(t instanceof SynchronizedEvent){
            tvShow.setText(((Weather2)((SynchronizedEvent)t).getDatas().get(0)).getCountry());
        }else if(t instanceof Weather2){
            tvWeatherShow.setText(((Weather2)t).getCountry());
        }
    }

    public void getWeatherData(){
        mSubscriptions.add(registerEventCallBack(Weather2.class));
        mSubscriptions.add(registerEventCallBack(SynchronizedEvent.class));
        showLoading(R.string.label_being_something);
        mCompositeSubscription.add(mWeatherPresenter.getWeatherData());
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
    protected void onDestroy() {
        super.onDestroy();
        mWeatherPresenter = null;
    }
}
