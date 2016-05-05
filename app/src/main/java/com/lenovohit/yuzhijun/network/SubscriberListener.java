package com.lenovohit.yuzhijun.network;

/**
 * Created by lenovo on 2016/3/30.
 */
public interface SubscriberListener<T>{
    void onNext(T t, int flag);
    void onComplete(int flag);
    void onError(Throwable e);
    void preStart();
}
