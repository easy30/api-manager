package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmDomain;

public class AmDomainResDto extends AmDomain {
    private static final long serialVersionUID = -9114645786981082741L;
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
