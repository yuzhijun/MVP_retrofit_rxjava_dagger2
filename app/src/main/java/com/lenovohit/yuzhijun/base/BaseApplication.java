package com.lenovohit.yuzhijun.base;

import android.app.Application;
import android.content.Context;

import com.lenovohit.yuzhijun.inject.component.AppComponent;
import com.lenovohit.yuzhijun.inject.component.DaggerAppComponent;
import com.lenovohit.yuzhijun.inject.module.AppModule;

/**
 * 基类application
 * Created by yuzhijun on 2016/4/28.
 */
public class BaseApplication extends Application {
    private AppComponent appComponent;

    public static BaseApplication mBaseApplication;

//    public static RefWatcher getRefWatcher(Context context) {
//        BaseApplication application = (BaseApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }
//
//    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;
//        refWatcher = LeakCanary.install(this);
        setupGraph();
    }

    private void setupGraph() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }

    public static BaseApplication get(Context context){
        return (BaseApplication)context.getApplicationContext();
    }

    public static BaseApplication getBaseApplication(){
        return mBaseApplication;
    }

    public AppComponent component(){
        return this.appComponent;
    }

}
