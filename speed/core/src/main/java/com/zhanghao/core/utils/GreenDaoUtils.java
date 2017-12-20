package com.zhanghao.core.utils;

import android.database.sqlite.SQLiteDatabase;

import com.zhanghao.core.gen.DaoMaster;
import com.zhanghao.core.gen.DaoSession;

/**
 * 作者： zhanghao on 2017/10/11.
 * 功能：${des}
 */

public class GreenDaoUtils {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private volatile  static GreenDaoUtils instance;


    private GreenDaoUtils() {
        mHelper = new DaoMaster.DevOpenHelper(AppManager.I().getApplicationContext(), "speed-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public static GreenDaoUtils getInstance() {
        if (instance == null) {
            synchronized (GreenDaoUtils.class) {
                if (instance == null) {
                    instance = new GreenDaoUtils();
                }
            }
        }
        return instance;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
