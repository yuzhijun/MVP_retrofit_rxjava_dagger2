package com.lenovohit.yuzhijun.ui.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovohit.yuzhijun.R;
import com.lenovohit.yuzhijun.inject.component.AppComponent;
import com.lenovohit.yuzhijun.inject.component.DaggerActivityComponent;
import com.lenovohit.yuzhijun.inject.module.ActivityModule;
import com.lenovohit.yuzhijun.model.Weather2;
import com.lenovohit.yuzhijun.model.event.SynchronizedEvent;
import com.lenovohit.yuzhijun.ui.presenter.WeatherPresenter;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by yuzhijun on 2016/4/28.
 */
public class MainActivity extends SimpleActivity{

    @Bind(R.id.tvShow)
    TextView tvShow;

    @Bind(R.id.tvWeatherShow)
    TextView tvWeatherShow;

    @Bind(R.id.btnSwitch)
    Button btnSwitch;

    @Inject
    WeatherPresenter mWeatherPresenter;


    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_main);
        setToolBarTitle("主页");
        setIconToolBarRight(R.mipmap.ic_launcher, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        getWeatherData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected <T> void rxbusCallBack(T t) {
        cancelLoading();
        if(t instanceof SynchronizedEvent){
            tvShow.setText(((Weather2)((SynchronizedEvent)t).getDatas().get(0)).getCountry());
        }else if(t instanceof Weather2){
            tvWeatherShow.setText(((Weather2)t).getCountry());
        }
    }

    public void getWeatherData(){
        mSubscriptions.add(registerEventCallBack(Weather2.class));
        mSubscriptions.add(registerEventCallBack(SynchronizedEvent.class));
        showLoading(R.string.label_being_something);
        mCompositeSubscription.add(mWeatherPresenter.getWeatherData());
    }

    /** 
      * 菜单、返回键响应 
      */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click();//调用双击退出函数  
        }
        return false;
    }

    /** 
      * 双击退出函数 
      */
    private static Boolean isExit = false;
    private void exitBy2Click(){
        Timer tExit = null;
        if(isExit == false){
            isExit = true;// 准备退出  
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask(){
                @Override
                public void run() {
                    isExit = false;// 取消退出  
                }
            },2000);// 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务  
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWeatherPresenter = null;
    }
}
