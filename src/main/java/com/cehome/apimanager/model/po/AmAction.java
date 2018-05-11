package com.cehome.apimanager.model.po;

import com.cehome.apimanager.common.BaseEntity;
import com.cehome.apimanager.common.FiledDesc;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 接口
 *
 * @author sunlei
 */
public class AmAction extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8747150818253864391L;
    /**
     * 接口编号
     */
    private Integer id;
    /**
     * 接口名称
     */
    @FiledDesc(desc = "接口名称")
    private String actionName;
    /**
     * 接口描述
     */
    @FiledDesc(desc = "接口描述")
    private String actionDesc;
    /**
     * 接口所属模块
     */
    @FiledDesc(desc = "所属模块")
    private Integer moduleId;
    /**
     * 接口请求类型
     */
    @FiledDesc(desc = "请求类型")
    private Integer requestType;
    /**
     * 域名的id
     */
    @FiledDesc(desc = "域名id")
    private Integer domainId;
    /**
     * 接口请求url
     */
    @FiledDesc(desc = "接口请求Url")
    private String requestUrl;
    /**
     * 接口状态
     */
    private Integer status;
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
     * 创建人
     */
    private Integer createUser;
    /**
     * 创建时间
     */
    private Date createTime;
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

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    public String getResponseFailDefinition() {
        return responseFailDefinition;
    }

    public void setResponseFailDefinition(String responseFailDefinition) {
        this.responseFailDefinition = responseFailDefinition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmAction amAction = (AmAction) o;
        return Objects.equals(actionName, amAction.actionName) &&
                Objects.equals(actionDesc, amAction.actionDesc) &&
                Objects.equals(moduleId, amAction.moduleId) &&
                Objects.equals(requestType, amAction.requestType) &&
                Objects.equals(domainId, amAction.domainId) &&
                Objects.equals(requestUrl, amAction.requestUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(actionName, actionDesc, moduleId, requestType, domainId, requestUrl);
    }
}
