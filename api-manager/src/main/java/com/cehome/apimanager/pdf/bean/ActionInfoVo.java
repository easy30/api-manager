package com.cehome.apimanager.pdf.bean;


import com.cehome.apimanager.pdf.AbstractDocumentVo;

import java.util.List;
import java.util.Map;

public class ActionInfoVo extends AbstractDocumentVo {
    private String imagePath;
    private String actionName;
    private String actionDesc;
    private String method;
    private String actionUrl;
    private List<Map<String, Object>> requestHeadParams;
    private List<Map<String, Object>> requestParams;
    private List<Map<String, Object>> responseParams;
    private List<Map<String, Object>> responseFailParams;
    private String responseJson;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public List<Map<String, Object>> getRequestHeadParams() {
        return requestHeadParams;
    }

    public void setRequestHeadParams(List<Map<String, Object>> requestHeadParams) {
        this.requestHeadParams = requestHeadParams;
    }

    public List<Map<String, Object>> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(List<Map<String, Object>> requestParams) {
        this.requestParams = requestParams;
    }

    public List<Map<String, Object>> getResponseParams() {
        return responseParams;
    }

    public void setResponseParams(List<Map<String, Object>> responseParams) {
        this.responseParams = responseParams;
    }

    public List<Map<String, Object>> getResponseFailParams() {
        return responseFailParams;
    }

    public void setResponseFailParams(List<Map<String, Object>> responseFailParams) {
        this.responseFailParams = responseFailParams;
    }

    public String getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(String responseJson) {
        this.responseJson = responseJson;
    }

    @Override
    public String findPrimaryKey() {
        return System.currentTimeMillis() + "";
    }
}
