package com.cehome.apimanager.model.po;

import com.cehome.apimanager.common.BaseEntity;
import com.cehome.apimanager.common.FiledDesc;

import java.io.Serializable;
import java.util.Objects;

public class AmEnv extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -7214970638407885986L;
    private Integer id;
    @FiledDesc(desc = "环境名称")
    private String envName;
    @FiledDesc(desc = "排序编号")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmEnv amEnv = (AmEnv) o;
        return Objects.equals(envName, amEnv.envName) &&
                Objects.equals(sortCode, amEnv.sortCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(envName, sortCode);
    }
}
