package com.cehome.apimanager.model.po;

import java.io.Serializable;
import java.util.Date;

public class AmActionHistory implements Serializable {
    private static final long serialVersionUID = -8402390055861769457L;
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 接口编号
     */
    private Integer actionId;
    /**
     * 接口名称
     */
    private String actionName;
    /**
     * 接口描述
     */
    private String actionDesc;
    /**
     * 接口所属模块
     */
    private Integer moduleId;
    /**
     * 接口请求类型
     */
    private Integer requestType;
    /**
     * 域名的id
     */
    private Integer domainId;
    /**
     * 接口请求url
     */
    private String requestUrl;
    /**
     * 接口请求头定义
     */
    private String requestHeadDefinition;
    /**
     * 接口请求参数定义
     */
    private String requestDefinition;
    /**
     * 接口返回结果定义
     */
    private String responseDefinition;
    /**
     * 响应失败结果定义
     */
    private String responseFailDefinition;
    /**
     * 接口请求头mock模板定义
     */
    private String requestHeadMock;
    /**
     * 接口请求参数mock模板定义
     */
    private String requestMock;
    /**
     * 接口响应模板定义
     */
    private String responseMock;
    /**
     * 修改人
     */
    private Integer updateUser;
    /**
     * 修改时间
     */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
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

    public String getRequestHeadDefinition() {
        return requestHeadDefinition;
    }

    public void setRequestHeadDefinition(String requestHeadDefinition) {
        this.requestHeadDefinition = requestHeadDefinition;
    }

    public String getRequestDefinition() {
        return requestDefinition;
    }

    public void setRequestDefinition(String requestDefinition) {
        this.requestDefinition = requestDefinition;
    }

    public String getResponseDefinition() {
        return responseDefinition;
    }

    public void setResponseDefinition(String responseDefinition) {
        this.responseDefinition = responseDefinition;
    }

    public String getResponseFailDefinition() {
        return responseFailDefinition;
    }

    public void setResponseFailDefinition(String responseFailDefinition) {
        this.responseFailDefinition = responseFailDefinition;
    }

    public String getRequestHeadMock() {
        return requestHeadMock;
    }

    public void setRequestHeadMock(String requestHeadMock) {
        this.requestHeadMock = requestHeadMock;
    }

    public String getRequestMock() {
        return requestMock;
    }

    public void setRequestMock(String requestMock) {
        this.requestMock = requestMock;
    }

    public String getResponseMock() {
        return responseMock;
    }

    public void setResponseMock(String responseMock) {
        this.responseMock = responseMock;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
