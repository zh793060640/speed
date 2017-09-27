package com.zhanghao.core.base.baserx;


import com.zhanghao.core.base.BaseRespose;
import com.zhanghao.core.utils.AppManager;
import com.zhanghao.core.utils.NetWorkStateUtils;
import com.zhanghao.core.utils.ToastUtils;

/**
 * des:服务器请求异常
 * Created by xsf
 * on 2016.09.10:16
 */
public class ServerException extends Exception {

    public ServerException(BaseRespose result) {
        //网络
        super(result.msg);
        if (!NetWorkStateUtils.isConnected(AppManager.I().currentActivity())) {
            ToastUtils.showShortToast("网络错误");
        }
        String code = result.code;
        //服务器
        if (code == CODE_SERVER_FAIL) {
            ToastUtils.showShortToast("服务器异常");
        } else {

            ToastUtils.showShortToast("result.msg");
        }

    }

    /**
     * 请求成功返回代码
     */
    public static final String CODE_REQUEST_SUCCESS = "200";
    /**
     * 请求失败
     */
    public static final String CODE_REQUES_FAIL = "110";
    /**
     * 服务器异常
     */
    public static final String CODE_SERVER_FAIL = "403";
    /**
     * 身份验证失效
     */
    public static final String CODE_SIGNATURE_OUTDATE = "121";
    /**
     * 请求signature身份失效
     */
    public static final String CODE_REQUEST_SIGNATURE_FAIL = "111";
    /**
     * 网络异常
     */
    public static final String CODE_NET_ERROR = "1000";
    /**
     * 数据异常
     */
    public static final String CODE_DATA_ERROR = "1001";
    /**
     * 数据处理异常
     */
    public static final String CODE_DEAL_DATA_ERROR = "1002";
    /**
     * 获取身份验证信息异常
     */
    public static final String CODE_SIGNATURE_ERROR = "1003";
    /**
     * 未知错误
     */
    public static final String CODE_UNKNOWN_ERROR = "1004";
}
