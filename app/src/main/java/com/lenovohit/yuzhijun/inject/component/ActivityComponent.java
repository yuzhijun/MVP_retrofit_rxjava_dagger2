package com.lenovohit.yuzhijun.inject.component;


import com.lenovohit.yuzhijun.inject.module.ActivityModule;
import com.lenovohit.yuzhijun.inject.module.ActivityScope;
import com.lenovohit.yuzhijun.ui.activity.MainActivity;
import com.lenovohit.yuzhijun.ui.activity.SecondActivity;
import com.lenovohit.yuzhijun.ui.presenter.WeatherPresenter;

import dagger.Component;

/**
 * Created by yuzhijun on 2016/4/8.
 */
@ActivityScope
@Component(dependencies = AppComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(SecondActivity secondActivity);

    WeatherPresenter presenter();
}
