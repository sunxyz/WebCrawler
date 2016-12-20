package cn.sunxyz.webcrawler.scheduler.cache;

import cn.sunxyz.webcrawler.Request;

public interface Cache {

	void addVisitedCache(Request request);
	
	void addNotVisitedCache(Request request);
	
}
