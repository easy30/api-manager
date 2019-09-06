package com.cehome.apimanager.model.po;

import com.cehome.apimanager.common.BaseEntity;
import com.cehome.apimanager.common.FiledDesc;

import java.io.Serializable;
import java.util.Objects;

public class AmDomain extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 3306432673609817811L;
    private Integer id;
    @FiledDesc(desc = "所属环境")
    private Integer envId;
    private Integer domainCode;
    @FiledDesc(desc = "服务地址")
    private String domainName;
    @FiledDesc(desc = "排序编号")
    private Integer sortCode;
    private Integer createUser;
    private Integer updateUser;
    private Integer projectId;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEnvId() {
        return envId;
    }

    public void setEnvId(Integer envId) {
        this.envId = envId;
    }

    public Integer getDomainCode() {
        return domainCode;
    }

    public void setDomainCode(Integer domainCode) {
        this.domainCode = domainCode;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Integer getSortCode() {
        return sortCode;
    }

    public void setSortCode(Integer sortCode) {
        this.sortCode = sortCode;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmDomain domain = (AmDomain) o;
        return Objects.equals(envId, domain.envId) &&
                Objects.equals(domainName, domain.domainName) &&
                Objects.equals(sortCode, domain.sortCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(envId, domainName, sortCode);
    }
}
