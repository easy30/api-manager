package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmTestGroup;

public class AmTestGroupResDto extends AmTestGroup {
    private static final long serialVersionUID = -6385997672084716958L;
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
