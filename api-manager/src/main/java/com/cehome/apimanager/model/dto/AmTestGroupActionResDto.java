package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmTestGroupAction;

public class AmTestGroupActionResDto extends AmTestGroupAction {
    private static final long serialVersionUID = -7370744953752613860L;
    private String createUserName = "";
    private String updateUserName = "";
    private String actionName;
    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
