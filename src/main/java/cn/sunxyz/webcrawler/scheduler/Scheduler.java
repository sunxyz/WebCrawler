package cn.sunxyz.webcrawler.scheduler;

import java.util.Collection;

import cn.sunxyz.webcrawler.Request;
import cn.sunxyz.webcrawler.scheduler.cache.Cache;

public interface Scheduler {

	//设置缓存和日志记录与链接管理
	void setCache(Cache... caches);

	Request pop();

	void push(String... urls);

	void push(Collection<Request> requests);
	
	int size();

}
