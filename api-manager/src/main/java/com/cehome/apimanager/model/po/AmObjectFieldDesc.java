package com.cehome.apimanager.model.po;

import java.io.Serializable;
import java.util.Date;

public class AmObjectFieldDesc implements Serializable {
    private static final long serialVersionUID = 1429431031509508548L;
    private Integer id;
    private String classWholeName;
    private String tableName;
    private String fieldDescValue;
    private String fieldInfoValue;
    private Integer createUser;
    private Date createTime;
    private Integer updateUser;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassWholeName() {
        return classWholeName;
    }

    public void setClassWholeName(String classWholeName) {
        this.classWholeName = classWholeName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldDescValue() {
        return fieldDescValue;
    }

    public void setFieldDescValue(String fieldDescValue) {
        this.fieldDescValue = fieldDescValue;
    }

    public String getFieldInfoValue() {
        return fieldInfoValue;
    }

    public void setFieldInfoValue(String fieldInfoValue) {
        this.fieldInfoValue = fieldInfoValue;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
