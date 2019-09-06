package com.cehome.apimanager.cache;

import com.cehome.apimanager.model.po.AmAction;
import com.cehome.apimanager.model.po.AmUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地内存缓存
 * 
 * @author sunlei
 *
 */
@Component
public class LocalCacheProvider implements CacheProvider{
	private  List<AmAction> actionUrlCache = new ArrayList<>();

	private Map<String, String> userDicMap = new ConcurrentHashMap<>();

	public Map<String, String> getUserDicMap() {
		return userDicMap;
	}

	public void setUserDicMap(Map<String, String> userDicMap) {
		this.userDicMap = userDicMap;
	}

	public List<AmAction> getActionUrlCache() {
		return actionUrlCache;
	}

	public void setActionUrlCache(List<AmAction> actionUrlCache) {
		this.actionUrlCache = actionUrlCache;
	}

	public synchronized void addUserDic(AmUser user){
		userDicMap.put(user.getId() + "", user.getUserName());
	}

	public synchronized void removeUserDic(AmUser user){
		userDicMap.remove(user.getId() + "");
	}

	public synchronized void addActionUrlCache(AmAction action){
		actionUrlCache.add(action);
	}
	
	public synchronized void removeActionUrlCache(AmAction action){
		Iterator<AmAction> iterator = actionUrlCache.iterator();
		while(iterator.hasNext()){
			AmAction next = iterator.next();
			if(next.getRequestUrl().equals(action.getRequestUrl())){
				iterator.remove();
				break;
			}
		}
	}
}
