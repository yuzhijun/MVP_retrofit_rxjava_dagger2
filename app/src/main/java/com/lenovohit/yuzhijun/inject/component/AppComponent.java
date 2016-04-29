package com.lenovohit.yuzhijun.inject.component;

import android.app.Application;

import com.lenovohit.yuzhijun.base.BaseApplication;
import com.lenovohit.yuzhijun.base.BaseServiceFactory;
import com.lenovohit.yuzhijun.inject.module.ApiServiceModule;
import com.lenovohit.yuzhijun.inject.module.AppModule;
import com.lenovohit.yuzhijun.network.ApiService;
import com.lenovohit.yuzhijun.ui.presenter.BasePresenter;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;


/**
 * Created by yuzhijun on 2016/4/8.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        ApiServiceModule.class
})
public interface AppComponent {
    BaseApplication getApplication();
    ApiService getService();

    void inject(BaseApplication app);
    void inject(BaseServiceFactory serviceFactory);
    void inject(BasePresenter basePresenter);
}
