package com.cehome.apimanager.model.po;

import java.io.Serializable;

public class AmActionLogin implements Serializable {
    private static final long serialVersionUID = -388432148140905308L;
    private Integer id;
    private Integer domainId;
    private String requestUrl;
    private Integer requestType;
    private String accountParam;
    private String extParam;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public String getAccountParam() {
        return accountParam;
    }

    public void setAccountParam(String accountParam) {
        this.accountParam = accountParam;
    }

    public String getExtParam() {
        return extParam;
    }

    public void setExtParam(String extParam) {
        this.extParam = extParam;
    }
}
