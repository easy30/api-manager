package com.cehome.apimanager.model.po;

import com.cehome.apimanager.common.BaseEntity;

import java.io.Serializable;
import java.util.Objects;

public class AmImportTemplate extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -2417180764192341809L;
    private Integer id;
    private String templateName;
    private String templateContent;
    private Integer templateType;
    private Integer sortCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
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
        AmImportTemplate that = (AmImportTemplate) o;
        return Objects.equals(templateName, that.templateName) &&
                Objects.equals(templateContent, that.templateContent) &&
                Objects.equals(templateType, that.templateType) &&
                Objects.equals(sortCode, that.sortCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(templateName, templateContent, templateType, sortCode);
    }
}
