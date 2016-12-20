package cn.sunxyz.webcrawler.queue;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CrawlQueue<R extends Serializable> implements Queue<R> {

	private BlockingQueue<R> queue = new LinkedBlockingQueue<R>();

	@Override
	public void push(R request) {
		queue.add(request);
	}

	@Override
	public R pop() {
		return queue.poll();
	}

	@Override
	public int size() {
		return queue.size();
	}


}
