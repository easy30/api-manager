package com.cehome.apimanager.cache;

import java.util.List;

import com.cehome.apimanager.model.po.AmAction;

public interface CacheProvider {
	List<AmAction> getActionUrlCache();

	void setActionUrlCache(List<AmAction> actionUrlCache);

	void addActionUrlCache(AmAction action);

	void removeActionUrlCache(AmAction action);
}
