package cn.sunxyz.webcrawler;

import java.util.Set;

import cn.sunxyz.webcrawler.Request.RequestLinkType;

public class WSprider extends AbstratSprider implements Runnable {

	@Override
	public void run() {
		start(fetchType);
	}

	public WSprider(Class<?> clazz, String... urls) {
		init(clazz, pipeline, urls);
	}

	void download() {
		while (scheduler.size() > 0) {
			this.sleep();
			Request request = scheduler.pop();
			Page page = downLoader.dowloader(request);
			if (page.getStatus()) {
				Set<Request> requests = linksFilter.getRequests(page);
				if (!RequestLinkType.HELP.equals(request.getMethod())) {
					Object owner = builderAdapter.buildOwner(page);// 注值
					pipeline.process(owner, new Site(page, requests));// 流处理
				}
				scheduler.push(requests);
			}
		}
	}

	@Override
	void download(FetchType fetchType) {
		switch (fetchType) {
		case Eager:
			this.download();
			break;
		default:
			while (scheduler.size() > 0) {
				this.sleep();
				Request request = scheduler.pop();
				Page page = downLoader.dowloader(request);
				if (page.getStatus()) {
					if (!RequestLinkType.HELP.equals(request.getMethod())) {
						Object owner = builderAdapter.buildOwner(page);// 注值
						pipeline.process(owner, new Site(page));// 流处理
					}
				}
			}
			break;
		}
	}

	@SuppressWarnings("static-access")
	private void sleep() {
		try {
			Thread.currentThread().sleep(sleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
