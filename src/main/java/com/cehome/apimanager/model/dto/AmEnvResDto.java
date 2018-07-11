package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmEnv;

public class AmEnvResDto extends AmEnv {
    private static final long serialVersionUID = -2025059246653367129L;
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
