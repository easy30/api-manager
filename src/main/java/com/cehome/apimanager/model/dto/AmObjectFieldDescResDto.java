package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmObjectFieldDesc;

public class AmObjectFieldDescResDto extends AmObjectFieldDesc {
    private static final long serialVersionUID = -7662483509324032345L;

    private String createUserName = "";
    private String updateUserName = "";

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
}
