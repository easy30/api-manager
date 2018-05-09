package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmActionLogin;

public class AmActionLoginResDto extends AmActionLogin{
    private static final long serialVersionUID = 1237076128124401072L;
    private String domainName;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
