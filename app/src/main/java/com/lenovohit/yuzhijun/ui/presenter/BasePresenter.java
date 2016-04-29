package com.lenovohit.yuzhijun.ui.presenter;

import com.lenovohit.yuzhijun.base.BaseApplication;
import com.lenovohit.yuzhijun.inject.component.AppComponent;
import com.lenovohit.yuzhijun.inject.component.DaggerAppComponent;
import com.lenovohit.yuzhijun.inject.module.ApiServiceModule;
import com.lenovohit.yuzhijun.inject.module.AppModule;
import com.lenovohit.yuzhijun.network.ServiceFactory;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by yuzhijun on 2016/4/29.
 */
public abstract class BasePresenter {
    @Inject
    protected ServiceFactory mServiceFactory;

    private AppComponent appComponent;

    public BasePresenter(){
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(BaseApplication.getBaseApplication()))
                .apiServiceModule(new ApiServiceModule())
                .build();
        appComponent.inject(this);
    }
}
