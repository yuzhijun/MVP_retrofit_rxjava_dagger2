package com.lenovohit.yuzhijun.ui.presenter;

import com.lenovohit.yuzhijun.base.BaseSubscriber;
import com.lenovohit.yuzhijun.model.Weather2;
import com.lenovohit.yuzhijun.model.XMModel;
import com.lenovohit.yuzhijun.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by yuzhijun on 2016/4/29.
 */
public class MainActivityPresenter extends BasePresenter{
    private List<XMModel> xmModels = new ArrayList<>();
    private static final String IP = "63.223.108.42";
    public MainActivity mMainActivity;

    public MainActivityPresenter(MainActivity mainActivity){
        this.mMainActivity = mainActivity;
    }

    public Subscription getWeatherData(){
        Subscription subscription =  mServiceFactory.getWeather6(new BaseSubscriber<>(this, 1), IP);
        return subscription;
    }

    public Subscription getXMList(){
        Subscription subscription = mServiceFactory.getXMList(new BaseSubscriber<>(this,2),0,1);
        return  subscription;
    }

    @Override
    public void onNext(Object o, int flag) {
        switch (flag) {
            case 1:
                Weather2 weather2 = (Weather2)o;
                mMainActivity.setTvShow(weather2.getCountry());
                break;
            case 2:
                XMModel xmModel = (XMModel) o;
                xmModels.add(xmModel);
            default:
                break;
        }
    }

    @Override
    public void onComplete(int flag) {
        super.onComplete(flag);
        switch (flag){
            case 2:
                mMainActivity.setTvShow(xmModels.get(0).getBT());
                break;
            default:
                break;
        }
    }
}
