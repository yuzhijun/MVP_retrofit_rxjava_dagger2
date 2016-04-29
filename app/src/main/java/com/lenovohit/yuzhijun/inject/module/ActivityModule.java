package com.lenovohit.yuzhijun.inject.module;

import android.app.Activity;

import com.lenovohit.yuzhijun.ui.activity.MainActivity;
import com.lenovohit.yuzhijun.ui.presenter.MainActivityPresenter;

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
    MainActivityPresenter provideMainActivityPresenter(MainActivity activity){
        return  new MainActivityPresenter(activity);
    }
}
