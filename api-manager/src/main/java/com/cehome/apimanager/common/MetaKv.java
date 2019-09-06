package com.cehome.apimanager.common;

import java.util.Map;

public class MetaKv {
    private Integer k;
    private String v;

    public MetaKv(int k, String v) {
        this.k = k;
        this.v = v;
    }

    public MetaKv(Map.Entry<Integer, String> mapEntry) {
        this.k = mapEntry.getKey();
        this.v = mapEntry.getValue();
    }

    public Integer getK() {
        return k;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }
}
