package com.zhanghao.speed.speed.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by PC on 2017/3/7.
 * 作者 ：张浩
 * 作用：
 */
@Entity
public class User implements Serializable {
    @Id
    private Long id;
    private String userName;
    private String userId;
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 353924413)
    public User(Long id, String userName, String userId) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
    }
    @Generated(hash = 586692638)
    public User() {
    }

}
