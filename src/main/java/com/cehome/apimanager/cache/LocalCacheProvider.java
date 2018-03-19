package com.cehome.apimanager.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cehome.apimanager.model.po.AmAction;

/**
 * 本地内存缓存
 * 
 * @author sunlei
 *
 */
@Component
public class LocalCacheProvider implements CacheProvider{
	private  List<AmAction> actionUrlCache = new ArrayList<>();

	public List<AmAction> getActionUrlCache() {
		return actionUrlCache;
	}

	public void setActionUrlCache(List<AmAction> actionUrlCache) {
		this.actionUrlCache = actionUrlCache;
	}
	
	public synchronized void addActionUrlCache(AmAction action){
		actionUrlCache.add(action);
	}
	
	public synchronized void removeActionUrlCache(AmAction action){
		Iterator<AmAction> iterator = actionUrlCache.iterator();
		while(iterator.hasNext()){
			AmAction next = iterator.next();
			if(next.getRequestUrl().equals(next.getRequestUrl())){
				iterator.remove();
				break;
			}
		}
	}
}
