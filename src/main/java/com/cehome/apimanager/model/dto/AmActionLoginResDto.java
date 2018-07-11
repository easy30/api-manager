package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmActionLogin;

public class AmActionLoginResDto extends AmActionLogin{
    private static final long serialVersionUID = 1237076128124401072L;
    private String domainName;
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
    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
