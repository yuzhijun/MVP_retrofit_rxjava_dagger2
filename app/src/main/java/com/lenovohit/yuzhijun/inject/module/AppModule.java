package com.lenovohit.yuzhijun.inject.module;

import com.lenovohit.yuzhijun.base.BaseApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuzhijun on 2016/4/8.
 */
@Module
public class AppModule {

    private BaseApplication app;

    public AppModule(BaseApplication app){
        this.app = app;
    }

    @Provides
    @Singleton
    public BaseApplication provideApp(){
        return this.app;
    }
}
