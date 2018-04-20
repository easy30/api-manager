package com.cehome.apimanager.model.po;

import java.io.Serializable;

public class AmEnv implements Serializable {
    private static final long serialVersionUID = -7214970638407885986L;
    private Integer id;
    private String envName;
    private Integer sortCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public Integer getSortCode() {
        return sortCode;
    }

    public void setSortCode(Integer sortCode) {
        this.sortCode = sortCode;
    }
}
