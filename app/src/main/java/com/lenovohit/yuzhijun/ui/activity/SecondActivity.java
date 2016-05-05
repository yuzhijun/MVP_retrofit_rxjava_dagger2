package com.lenovohit.yuzhijun.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.komi.slider.SliderUtils;
import com.lenovohit.yuzhijun.R;
import com.lenovohit.yuzhijun.inject.component.AppComponent;

import butterknife.Bind;

/**
 * Created by yuzhijun on 2016/5/5.
 */
public class SecondActivity extends SimpleActivity {
    @Bind(R.id.btnSwitch)
    Button btnSwitch;

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
    protected void initListener() {
        super.initListener();
        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this,ThirdActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
    }
}
