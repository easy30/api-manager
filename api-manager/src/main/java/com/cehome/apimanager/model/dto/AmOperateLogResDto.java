package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmOperateLog;

public class AmOperateLogResDto extends AmOperateLog {
    private static final long serialVersionUID = -2183572975185286714L;
    private String operateTimeFormat;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOperateTimeFormat() {
        return operateTimeFormat;
    }

    public void setOperateTimeFormat(String operateTimeFormat) {
        this.operateTimeFormat = operateTimeFormat;
    }
}
