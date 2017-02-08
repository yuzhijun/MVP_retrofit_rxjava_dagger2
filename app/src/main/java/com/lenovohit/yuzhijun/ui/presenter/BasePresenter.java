package com.lenovohit.yuzhijun.ui.presenter;

import com.lenovohit.yuzhijun.base.BaseActivity;
import com.lenovohit.yuzhijun.base.BaseApplication;
import com.lenovohit.yuzhijun.exception.ApiException;
import com.lenovohit.yuzhijun.inject.component.AppComponent;
import com.lenovohit.yuzhijun.inject.component.DaggerAppComponent;
import com.lenovohit.yuzhijun.inject.module.ApiServiceModule;
import com.lenovohit.yuzhijun.inject.module.AppModule;
import com.lenovohit.yuzhijun.network.ServiceFactory;
import com.lenovohit.yuzhijun.network.SubscriberListener;
import com.lenovohit.yuzhijun.util.ViewUtil;

import javax.inject.Inject;

/**
 * P层的基类(P层主要用于业务的处理)
 * Created by yuzhijun on 2016/4/29.
 */
public abstract class BasePresenter implements SubscriberListener{
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

    @Override
    public void onComplete(int flag) {

    }

    @Override
    public void onError(Throwable e) {
        BaseActivity.currentActivity.cancelLoading();
        ViewUtil.showToast(((ApiException)e).getMessage(),false);
    }

    @Override
    public void preStart() {

    }
}
