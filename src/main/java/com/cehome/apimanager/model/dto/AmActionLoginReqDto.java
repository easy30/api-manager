package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmActionLogin;

public class AmActionLoginReqDto extends AmActionLogin {
    private static final long serialVersionUID = -1416326042881966152L;
    private String domainName;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
