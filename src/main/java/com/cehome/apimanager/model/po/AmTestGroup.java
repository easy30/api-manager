package com.cehome.apimanager.model.po;

import java.io.Serializable;
import java.util.Date;

public class AmTestGroup implements Serializable{
    private static final long serialVersionUID = -7762906447006385525L;
    private Integer id;
    private String groupName;
    private Integer createUser;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
}
