package com.lenovohit.yuzhijun.model;

/**
 * 用来统一返回结果
 * 因为服务器端返回的格式code 和errormessage是一样的
 * 所不同的是data的内容
 * Created by yuzhijun on 2016/3/29.
 */
public class HttpResult<T> {
    private int code;

    private T data;

    private T resultList;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        data = data;
    }

    public T getResultList() {
        return resultList;
    }

    public void setResultList(T resultList) {
        this.resultList = resultList;
    }
}
