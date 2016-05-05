package com.lenovohit.yuzhijun.ui.activity;

import com.komi.slider.SliderUtils;
import com.lenovohit.yuzhijun.R;
import com.lenovohit.yuzhijun.inject.component.AppComponent;

/**
 * Created by yuzhijun on 2016/5/5.
 */
public class ThirdActivity extends SimpleActivity {
    @Override
    protected int getContentView() {
        return R.layout.content_main;
    }

    @Override
    protected void initView() {
        super.initView();
//        SliderUtils.attachActivity(this, null);
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {

    }
}
