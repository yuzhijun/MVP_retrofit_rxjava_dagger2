package com.lenovohit.yuzhijun.inject.module;

import android.app.Activity;

import com.lenovohit.yuzhijun.ui.activity.MainActivity;

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
    Activity provideAcitivity(){
        return this.activity;
    }

}
