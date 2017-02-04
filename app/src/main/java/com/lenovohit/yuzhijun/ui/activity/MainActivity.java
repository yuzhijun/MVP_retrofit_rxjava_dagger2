package com.lenovohit.yuzhijun.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lenovohit.yuzhijun.R;
import com.lenovohit.yuzhijun.inject.component.AppComponent;
import com.lenovohit.yuzhijun.inject.component.DaggerActivityComponent;
import com.lenovohit.yuzhijun.inject.module.ActivityModule;
import com.lenovohit.yuzhijun.ui.presenter.MainActivityPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Subscription;

/**
 * Created by yuzhijun on 2016/4/28.
 */
public class MainActivity extends SimpleActivity{

    @Bind(R.id.tvShow)
    TextView tvShow;

    @Bind(R.id.btnSwitch)
    Button btnSwitch;

    @Inject
    MainActivityPresenter mainActivityPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();

        getWeatherData();
//        getXMList();
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

    public void setTvShow(String country){
        tvShow.setText(country);
    }

    public void getWeatherData(){
        Subscription subscription =  mainActivityPresenter.getWeatherData();
        mCompositeSubscription.add(subscription);
    }

    public void getXMList(){
        Subscription subscription = mainActivityPresenter.getXMList();
        mCompositeSubscription.add(subscription);
    }
}
