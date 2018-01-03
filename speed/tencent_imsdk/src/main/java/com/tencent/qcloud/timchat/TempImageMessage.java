package com.tencent.qcloud.timchat;

import com.tencent.imsdk.TIMMessage;
import com.tencent.qcloud.timchat.model.ImageMessage;

/**
 * 作者： zhanghao on 2017/9/29.
 * 功能：${des}
 */

public class TempImageMessage extends ImageMessage {
    public int type;

    public TempImageMessage(TIMMessage message) {
        super(message);
    }

    public TempImageMessage(String path) {
        super(path);
    }

    public TempImageMessage(String path, boolean isOri) {
        super(path, isOri);
    }
}
