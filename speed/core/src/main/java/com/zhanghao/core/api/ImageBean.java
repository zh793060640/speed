package com.zhanghao.core.api;

import java.io.Serializable;

/**
 * 作者： zhanghao on 2017/10/18.
 * 功能：${des}
 */

public class ImageBean implements Serializable {
    /**
     * code : 200
     * msg : 成功
     * portrait : https://test.leeonedu.com/Uploads/Portrait/69978_1508305640.png
     */

    private String code;
    private String msg;
    private String portrait;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
