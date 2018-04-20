package com.cehome.apimanager.model.po;

import java.io.Serializable;

public class AmDomain implements Serializable {
    private static final long serialVersionUID = 3306432673609817811L;
    private Integer id;
    private Integer envId;
    private Integer domainCode;
    private String domainName;
    private Integer sortCode;

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
}
