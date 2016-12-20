package cn.sunxyz.webcrawler.scheduler.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sunxyz.webcrawler.Request;

public class DefaultCache implements Cache {
	
	private static Logger logger = LoggerFactory.getLogger(DefaultCache.class);

	@Override
	public void addVisitedCache(Request request) {
		logger.debug("-------vis--------");
		logger.debug(request.getUrl());
	}

	@Override
	public void addNotVisitedCache(Request request) {
		logger.debug("-------not--------");
		logger.debug(request.getUrl());
	}

	
}
