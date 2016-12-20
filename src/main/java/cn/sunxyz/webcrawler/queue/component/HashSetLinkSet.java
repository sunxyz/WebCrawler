package cn.sunxyz.webcrawler.queue.component;

import java.util.Set;
import cn.sunxyz.webcrawler.Request;
import cn.sunxyz.webcrawler.utils.ConcurrentHashSet;

public class HashSetLinkSet<R> implements LinkSet<R> {

	private Set<String> urls = new ConcurrentHashSet<>();

	@Override
	public void push(R request) {
		urls.add(getUrl(request));
	}
	
	@Override
	public boolean hasExisted(R request) {
		return urls.add(getUrl(request));
	}

	protected String getUrl(R request) {
		if (request instanceof Request) {
			return ((Request) request).getUrl();
		} else {
			return request.toString();
		}
	}

	@Override
	public void clear() {
		urls.clear();
	}

	@Override
	public int size() {
		return urls.size();
	}
}
