package com.lenovohit.yuzhijun.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenovohit.yuzhijun.R;
import com.lenovohit.yuzhijun.inject.component.AppComponent;
import com.lenovohit.yuzhijun.util.DensityUtil;
import com.lenovohit.yuzhijun.util.RxBus;
import com.lenovohit.yuzhijun.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * 基类activity
 * Created by yuzhijun on 2016/4/28.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected CompositeSubscription mCompositeSubscription
            = new CompositeSubscription();

    private LoadingDialog mLoading;
    public static BaseActivity currentActivity;
    protected List<Subscription> mSubscriptions = new ArrayList<>();
    private Toolbar mToolbar;
    private TextView toolbar_title;
    private Button btnRight;
    private LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity = this;
        setupComponent(BaseApplication.get(this).component());
        beforeContentView();
        obtainParam(getIntent());
        initBaseView();
        initView();
        ButterKnife.bind(this);
        initListener();
        initData();
    }

    private void initBaseView(){
        super.setContentView(R.layout.layout_base_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        btnRight = (Button) findViewById(R.id.btnRight);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        content = (LinearLayout) findViewById(R.id.content);
    }

    protected void isShowToolBar(boolean isShow){
        mToolbar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    protected void setToolBarTitle(String title){
        toolbar_title.setText(title == null || "".equalsIgnoreCase(title) ? "" : title);
    }

    protected void setStrToolBarRight(String str,View.OnClickListener onClickListener){
        if (str != null && !"".equalsIgnoreCase(str)){
            btnRight.setVisibility(View.VISIBLE);
            btnRight.setText(str);
            btnRight.setOnClickListener(onClickListener);
        }
    }

    protected void setIconToolBarRight(@DrawableRes int icon, View.OnClickListener onClickListener){
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setBackgroundResource(icon);
        ViewGroup.LayoutParams linearParams = btnRight.getLayoutParams();
        linearParams.height = DensityUtil.dip2px(this,26);
        linearParams.width = DensityUtil.dip2px(this,26);
        btnRight.setLayoutParams(linearParams);
        btnRight.setOnClickListener(onClickListener);
    }

    public void setContentView(@LayoutRes int layoutResID){
        getLayoutInflater().inflate(layoutResID,this.content);
    }

    public final void showLoading(@StringRes int textResId) {
        showLoading(getString(textResId));
    }

    public final void showLoading(String text) {
        cancelLoading();
        if (mLoading == null) {
            mLoading = new LoadingDialog(this);
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

    protected void start2Activity(Class<?> activity){
        Intent intent = new Intent(this,activity);
        this.startActivity(intent);
    }

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

    protected abstract void obtainParam(Intent intent);

    protected abstract void beforeContentView();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    protected abstract void setupComponent(AppComponent appComponent);

    protected abstract <T> void rxbusCallBack(T t);

    //fragment管理
    public BaseFragment addFragment(BaseFragment fragment) {
        List<BaseFragment> fragments = new ArrayList<BaseFragment>(1);
        fragments.add(fragment);

        List<BaseFragment> fragments2 = addFragments(fragments);
        return fragments2.get(0);
    }

    public List<BaseFragment> addFragments(List<BaseFragment> fragments) {
        List<BaseFragment> fragments2 = new ArrayList<BaseFragment>(fragments.size());

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        boolean commit = false;
        for (int i = 0; i < fragments.size(); i++) {
            // install
            BaseFragment fragment = fragments.get(i);
            int id = fragment.getContainerId();

            // exists
            BaseFragment fragment2 = (BaseFragment) fm.findFragmentById(id);
            if (fragment2 == null) {
                fragment2 = fragment;
                transaction.add(id, fragment);
                commit = true;
            }
            fragments2.add(i, fragment2);
        }

        if (commit) {
            try {
                transaction.commitAllowingStateLoss();
            } catch (Exception e) {
            }
        }
        return fragments2;
    }

    public BaseFragment switchContent(BaseFragment fragment) {
        return switchContent(fragment, false);
    }

    protected BaseFragment switchContent(BaseFragment fragment, boolean needAddToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(fragment.getContainerId(), fragment);
        if (needAddToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }
        try {
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {

        }
        return fragment;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        ButterKnife.unbind(this);
        mCompositeSubscription.unsubscribe();
        if (mSubscriptions != null && mSubscriptions.size() > 0){
            for (Subscription subscription : mSubscriptions){
                if (!subscription.isUnsubscribed()){
                    subscription.unsubscribe();
                }
            }
        }
    }
}
