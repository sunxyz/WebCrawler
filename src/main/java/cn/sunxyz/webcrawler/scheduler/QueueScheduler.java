package cn.sunxyz.webcrawler.scheduler;

import java.util.Collection;

import cn.sunxyz.webcrawler.Request;
import cn.sunxyz.webcrawler.Request.RequestLinkType;
import cn.sunxyz.webcrawler.queue.CrawlQueue;
import cn.sunxyz.webcrawler.queue.Queue;
import cn.sunxyz.webcrawler.queue.component.HashSetLinkSet;
import cn.sunxyz.webcrawler.queue.component.LinkSet;

public class QueueScheduler extends AbstractScheduler {
	// 待访问对列
	private Queue<Request> urlsQueue;
	// 所有符合规则的链接
	private LinkSet<String> urlsSet;
	// 已访问的队列
	private LinkSet<String> visitedSet;

	{
		urlsQueue = new CrawlQueue<>();
		urlsSet = new HashSetLinkSet<>();
		visitedSet = new HashSetLinkSet<>();
	}

	@Override
	public Request pop() {
		Request request = urlsQueue.pop();
		visitedSet.push(request.getUrl());
		this.notifyObserver(request, CacheType.VIS);
		return request;
	}

	@Override
	public void push(String... urls) {
		for (String url : urls) {
			if (urlsSet.hasExisted(url)) {
				Request request = new Request(url, RequestLinkType.OTHER);
				urlsQueue.push(request);
				this.notifyObserver(request, CacheType.NOT);
			}
		}
	}

	@Override
	public void push(Collection<Request> requests) {
		for (Request request : requests) {
			if (urlsSet.hasExisted(request.getUrl())) {
				urlsQueue.push(request);
				this.notifyObserver(request, CacheType.NOT);
			}
		}
	}

	@Override
	public int size() {
		return urlsQueue.size();
	}

}
