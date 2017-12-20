package com.zhanghao.core.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;

/**
 * 作者： zhanghao on 2017/9/7.
 * 功能：${des}
 */
@Entity
public class RegisterInfo implements Serializable {
    @Property
    private Long classId;
    @Id
    private Long id;
    @Property
    private String name;
    @Property
    private String age;
    public RegisterInfo(Long classId) {
        this.classId = classId;
    }

    @Generated(hash = 568272888)
    public RegisterInfo(Long classId, Long id, String name, String age) {
        this.classId = classId;
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Generated(hash = 1470244328)
    public RegisterInfo() {
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
