package com.lenovohit.yuzhijun.model.event;

import java.util.List;

/**
 * 用来承载List类型的事件
 * Created by yuzhijun on 2017/2/6.
 */
public class SynchronizedEvent<T> {
    private List<T> datas;

    public SynchronizedEvent(List<T> datas){
        this.datas = datas;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
