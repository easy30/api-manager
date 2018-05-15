package com.cehome.apimanager.model.po;

import com.cehome.apimanager.common.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class AmOperateLog extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -2104579239739603297L;
    private Integer id;
    /**
     * 业务主键id
     */
    private Integer entityId;
    /**
     * 操作模块
     */
    private Integer moduleCode;
    /**
     * 操作类型
     */
    private Integer operateType;
    /**
     * 操作描述
     */
    private String operateDesc;
    /**
     * 内容变化
     */
    private String contentChange;
    /**
     * 操作人员
     */
    private Integer operateUser;
    /**
     * 操作时间
     */
    private Date operateTime;

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(Integer moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public String getOperateDesc() {
        return operateDesc;
    }

    public void setOperateDesc(String operateDesc) {
        this.operateDesc = operateDesc;
    }

    public String getContentChange() {
        return contentChange;
    }

    public void setContentChange(String contentChange) {
        this.contentChange = contentChange;
    }

    public Integer getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(Integer operateUser) {
        this.operateUser = operateUser;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
}
