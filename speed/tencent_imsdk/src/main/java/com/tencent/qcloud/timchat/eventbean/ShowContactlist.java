package com.tencent.qcloud.timchat.eventbean;

import com.leeone.leeonecore.bean.Order;

/**
 * 作者： zhanghao on 2017/11/27.
 * 功能：${des}
 */

public class ShowContactlist  extends Order{

    public boolean showContact;

    public ShowContactlist(boolean showContact) {
        this.showContact = showContact;
    }
}
