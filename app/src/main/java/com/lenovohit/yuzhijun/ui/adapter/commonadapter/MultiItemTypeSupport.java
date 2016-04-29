package com.lenovohit.yuzhijun.ui.adapter.commonadapter;

/**
 * Created by yuzhijun on 2016/4/25.
 */
public interface MultiItemTypeSupport<T> {
    int getLayoutId(int itemType);
    int getItemViewType(int position, T t);
}
