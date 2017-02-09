package com.lenovohit.yuzhijun.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yuzhijun on 2017/2/8.
 */

public abstract class BaseFragment extends Fragment {
    /*这种内存不足的情况会导致许多问题，其中之一就是Fragment调用getActivity()的地方却返回null，
    报了空指针异常。解决办法就是在Fragment基类里设置一个Activity mActivity的全局变量，在
    onAttach(Activity activity)（使用onAttach(Context context)）里赋值，使用mActivity代替getActivity*/
    protected BaseActivity mActivity;
    //是否加载完成
    protected boolean mIsPrepare;
    //根view
    protected View rootView;
    //要动态添加到的地址id
    private int containerId;

    protected BaseActivity getHoldingActivity(){
        return mActivity;
    }

    /**
     *获取布局文件ID
     */
    protected abstract int getLayoutId();
    /**
     * 初始化视图
     * */
    protected abstract void initView(View view,Bundle savedInstanceState);
    /**
     * 获取传递进来的参数
     * */
    protected abstract void initData(Bundle arguments);

    protected abstract void initListener();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(),container,false);
        initData(getArguments());
        initView(rootView,savedInstanceState);
        mIsPrepare = true;
        initListener();
        return rootView;
    }

    protected <T extends View> T findViewById(int resId) {
        return (T) (getView().findViewById(resId));
    }

    public int getContainerId() {
        return containerId;
    }

    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsPrepare = false;
    }
}
