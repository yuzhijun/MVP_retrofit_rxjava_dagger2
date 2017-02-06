package com.lenovohit.yuzhijun.inject.module;

import com.lenovohit.yuzhijun.ui.activity.MainActivity;
import com.lenovohit.yuzhijun.ui.presenter.WeatherPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuzhijun on 2016/4/8.
 */
@Module
public class ActivityModule {
    private final MainActivity activity;

    public ActivityModule(MainActivity activity){
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    MainActivity provideAcitivity(){
        return this.activity;
    }

    @Provides
    @ActivityScope
    WeatherPresenter provideWeatherPresenter(){
        return  new WeatherPresenter();
    }
}
