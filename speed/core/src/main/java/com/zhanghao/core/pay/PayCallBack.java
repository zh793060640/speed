package com.zhanghao.core.pay;

/**
 * 作者： zhanghao on 2017/9/27.
 * 功能：${des}
 */

public interface PayCallBack {
    public void paySuccess(int payType);

    public void payFail(int payType);
}
