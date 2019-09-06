package com.cehome.apimanager.model.dto;

import java.io.Serializable;

public class ActionTesterReqDto implements Serializable {
    private static final long serialVersionUID = -2605480638104880624L;
    private Integer actionId;
    private Integer envId;
    private Integer requestType;
    private String domainName;
    private String requestUrl;
    private String requestHeadData;
    private String requestData;

    public Integer getActionId() {
        return actionId;
    }

    public Integer getEnvId() {
        return envId;
    }

    public void setEnvId(Integer envId) {
        this.envId = envId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestHeadData() {
        return requestHeadData;
    }

    public void setRequestHeadData(String requestHeadData) {
        this.requestHeadData = requestHeadData;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
