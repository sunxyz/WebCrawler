package cn.sunxyz.webcrawler.scheduler;

import cn.sunxyz.webcrawler.Request;
import cn.sunxyz.webcrawler.scheduler.cache.Cache;

public abstract class AbstractScheduler implements Scheduler {

	private Cache[] caches;

	@Override
	public void setCache(Cache... caches) {
		this.caches = caches;
	}

	protected void notifyObserver(Request request, CacheType cacheType) {
		if (caches != null) {
			for (Cache cache : caches) {
				switch (cacheType) {
				case NOT:
					cache.addNotVisitedCache(request);
					break;
				default:
					cache.addVisitedCache(request);
					break;
				}
			}
		}
	}

	protected enum CacheType {
		NOT, VIS
	}

}
