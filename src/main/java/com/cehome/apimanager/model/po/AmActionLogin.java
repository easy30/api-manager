package com.cehome.apimanager.model.po;

import com.cehome.apimanager.common.BaseEntity;
import com.cehome.apimanager.common.FiledDesc;

import java.io.Serializable;
import java.util.Objects;

public class AmActionLogin extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -388432148140905308L;
    private Integer id;
    @FiledDesc(desc = "服务地址")
    private Integer domainId;
    @FiledDesc(desc = "接口地址")
    private String requestUrl;
    @FiledDesc(desc = "请求类型")
    private Integer requestType;
    @FiledDesc(desc = "账号信息")
    private String accountParam;
    @FiledDesc(desc = "其他参数")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmActionLogin that = (AmActionLogin) o;
        return Objects.equals(domainId, that.domainId) &&
                Objects.equals(requestUrl, that.requestUrl) &&
                Objects.equals(requestType, that.requestType) &&
                Objects.equals(accountParam, that.accountParam) &&
                Objects.equals(extParam, that.extParam);
    }

    @Override
    public int hashCode() {

        return Objects.hash(domainId, requestUrl, requestType, accountParam, extParam);
    }
}
