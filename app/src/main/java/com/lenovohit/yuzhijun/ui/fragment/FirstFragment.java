package com.lenovohit.yuzhijun.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.lenovohit.yuzhijun.base.BaseFragment;

/**
 * Created by yuzhijun on 2017/2/9.
 */

public class FirstFragment extends BaseFragment {
    public static String MSG = "msg";
    private String msg;

    public static FirstFragment getInstance(String msg){
        FirstFragment firstFragment = new FirstFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(MSG,msg);
        firstFragment.setArguments(bundle);
        return firstFragment;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle arguments) {
        if (arguments != null){
            msg = (String) arguments.getSerializable(MSG);
        }
    }
}
