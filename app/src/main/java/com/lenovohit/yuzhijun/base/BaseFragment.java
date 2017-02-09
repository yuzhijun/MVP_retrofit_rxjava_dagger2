package com.lenovohit.yuzhijun.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lenovohit.yuzhijun.util.RxBus;
import com.lenovohit.yuzhijun.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yuzhijun on 2017/2/8.
 */

public abstract class BaseFragment extends Fragment {
    protected CompositeSubscription mCompositeSubscription
            = new CompositeSubscription();

    protected List<Subscription> mSubscriptions = new ArrayList<>();
    /*这种内存不足的情况会导致许多问题，其中之一就是Fragment调用getActivity()的地方却返回null，
    报了空指针异常。解决办法就是在Fragment基类里设置一个Activity mActivity的全局变量，在
    onAttach(Activity activity)（使用onAttach(Context context)）里赋值，使用mActivity代替getActivity*/
    protected BaseActivity mActivity;
    //是否加载完成
    protected boolean mIsPrepare;
    //fragment是否可见
    private boolean isVisible;
    //是否第一次加载
    private boolean isFirst=true;
    //根view
    protected View rootView;
    //要动态添加到的地址id
    private int containerId;

    private LoadingDialog mLoading;

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

    protected abstract <T> void rxbusCallBack(T t);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(),container,false);
        ButterKnife.bind(this,rootView);
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

    //注册回调
    protected final  <T> Subscription registerEventCallBack(Class<T> event){
        Subscription subscription = RxBus.getDefault().toObservable(event)
                .subscribe(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        rxbusCallBack(t);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        cancelLoading();
                    }
                });
        return subscription;
    }

    //----------------------loading------------------------------------------
    public final void showLoading(@StringRes int textResId) {
        showLoading(getString(textResId));
    }

    public final void showLoading(String text) {
        cancelLoading();
        if (mLoading == null) {
            mLoading = new LoadingDialog(getHoldingActivity());
            mLoading.setCancelable(true);
            mLoading.setCanceledOnTouchOutside(false);
        }
        mLoading.setTitle(text);
        mLoading.show();
    }

    public final void cancelLoading() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsPrepare = false;
        mCompositeSubscription.unsubscribe();
        if (mSubscriptions != null && mSubscriptions.size() > 0){
            for (Subscription subscription : mSubscriptions){
                if (!subscription.isUnsubscribed()){
                    subscription.unsubscribe();
                }
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }
}
