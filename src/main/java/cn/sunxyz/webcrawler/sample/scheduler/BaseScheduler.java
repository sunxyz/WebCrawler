package cn.sunxyz.webcrawler.sample.scheduler;

import java.util.Collection;

import cn.sunxyz.webcrawler.Request;
import cn.sunxyz.webcrawler.scheduler.Scheduler;
import cn.sunxyz.webcrawler.scheduler.cache.Cache;

public abstract class BaseScheduler implements Scheduler {
	
	private int size = 1;

	@Override
	public void setCache(Cache... caches) {
		
	}

	public abstract Request pop();

	@Override
	public void push(String... urls) {
		
	}

	@Override
	public void push(Collection<Request> requests) {
		
	}

	@Override
	public int size() {
		return size;
	}

	public void setSize(int size){
		this.size = size;
	}
}
