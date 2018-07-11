package com.cehome.apimanager.cache;

import com.cehome.apimanager.model.po.AmAction;
import com.cehome.apimanager.model.po.AmUser;

import java.util.List;
import java.util.Map;

public interface CacheProvider {
    List<AmAction> getActionUrlCache();

    void setActionUrlCache(List<AmAction> actionUrlCache);

    public void setUserDicMap(Map<String, String> userDicMap);

    public Map<String, String> getUserDicMap();

    void addActionUrlCache(AmAction action);

    void removeActionUrlCache(AmAction action);

    void addUserDic(AmUser user);

    void removeUserDic(AmUser user);
}
