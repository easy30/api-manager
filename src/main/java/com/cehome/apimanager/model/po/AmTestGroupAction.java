package com.cehome.apimanager.model.po;

import java.io.Serializable;

public class AmTestGroupAction implements Serializable {
    private static final long serialVersionUID = -6220659021629312356L;
    private Integer id;
    private Integer groupId;
    private Integer actionId;
    private Integer sortCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public Integer getSortCode() {
        return sortCode;
    }

    public void setSortCode(Integer sortCode) {
        this.sortCode = sortCode;
    }
}
