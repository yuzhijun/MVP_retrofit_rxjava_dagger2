package com.lenovohit.yuzhijun.base;


import com.lenovohit.yuzhijun.inject.component.AppComponent;
import com.lenovohit.yuzhijun.inject.component.DaggerAppComponent;
import com.lenovohit.yuzhijun.inject.module.ApiServiceModule;
import com.lenovohit.yuzhijun.inject.module.AppModule;
import com.lenovohit.yuzhijun.network.OverWeightFunc;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 网络操作类的父类
 * @author yuzhijun 2016/04/29
 * */
public class BaseServiceFactory {
    private AppComponent appComponent;

    public BaseServiceFactory(){
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(BaseApplication.getBaseApplication()))
                .apiServiceModule(new ApiServiceModule())
                .build();
        appComponent.inject(this);
    }

    protected <T> Subscription subscribe(Observable<T> observable, Subscriber<T> subscriber){
        return observable.compose(this.<T>applySchedulers())
                .subscribe(subscriber);
    }

    public <T> Subscription getOverWeightDatas(Subscriber<T> subscriber, OverWeightFunc.IOverWeight<T> overWeight){
        OverWeightFunc<T> overWeightFunc = new OverWeightFunc<>();
        overWeightFunc.registerIOverWeight(overWeight);
        return Observable.defer(overWeightFunc)
                .compose(this.<T>applySchedulers())
                .subscribe(subscriber);
    }

    final Observable.Transformer schedulersTransformer = new Observable.Transformer(){
        @Override
        public Object call(Object observable) {
            return ((Observable)observable).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    private <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }
}
