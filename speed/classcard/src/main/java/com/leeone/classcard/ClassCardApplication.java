package com.leeone.classcard;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * 作者： zhanghao on 2017/7/21.
 * 功能：${des}
 */

public class ClassCardApplication extends TinkerApplication {

    public ClassCardApplication(int tinkerFlags) {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.leeone.classcard.SampleApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }

    public ClassCardApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.leeone.classcard.SampleApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
