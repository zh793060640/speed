package com.leeone.core.base;


import java.io.Serializable;

/**
 * des:封装服务器返回数据
 * Created by xsf
 * on 2016.09.9:47
 */
public class BaseRespose<T> implements Serializable {
    public String code;
    public String msg;
    public T data;
    public String nums;

    public boolean success() {
        return "200".equals(code);
    }

    @Override
    public String toString() {
        return "BaseRespose{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", nums='" + nums + '\'' +
                '}';
    }
}

