package com.zhanghao.speed.speed.utils;

import com.zhanghao.speed.speed.FastApplication;
import com.zhanghao.speed.speed.bean.UserDao;

/**
 * Created by PC on 2017/3/7.
 * 作者 ：张浩
 * 作用：
 */

public class DbHelper {

    public static  UserDao getUserDao() {
        UserDao dao = FastApplication.instances.getDaoSession().getUserDao();
        return dao;
    }
}
