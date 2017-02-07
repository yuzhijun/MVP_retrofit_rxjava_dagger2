package com.lenovohit.yuzhijun.inject.module;

import android.content.Context;

import com.lenovohit.yuzhijun.ui.presenter.WeatherPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuzhijun on 2016/4/8.
 */
@Module
public class ActivityModule {
    private final Context mContext;

    public ActivityModule(Context activity){
        this.mContext = activity;
    }

    @Provides
    @ActivityScope
    WeatherPresenter provideWeatherPresenter(){
        return  new WeatherPresenter();
    }
}
