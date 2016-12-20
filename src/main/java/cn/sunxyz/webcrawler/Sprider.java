package cn.sunxyz.webcrawler;

import java.util.Set;

import cn.sunxyz.webcrawler.Request.RequestLinkType;
import cn.sunxyz.webcrawler.builder.Builder;
import cn.sunxyz.webcrawler.builder.OwnerBuilderAdapter;
import cn.sunxyz.webcrawler.builder.OwnerTreeBuilder;
import cn.sunxyz.webcrawler.download.DownLoader;
import cn.sunxyz.webcrawler.download.JsoupDownloader;
import cn.sunxyz.webcrawler.parser.link.LinksFilter;
import cn.sunxyz.webcrawler.pipeline.DeafultPipeLine;
import cn.sunxyz.webcrawler.pipeline.Pipeline;
import cn.sunxyz.webcrawler.scheduler.QueueScheduler;
import cn.sunxyz.webcrawler.scheduler.Scheduler;
import cn.sunxyz.webcrawler.scheduler.cache.Cache;

public class Sprider {

	private DownLoader downLoader;// 下载器

	private LinksFilter linksFilter;// 链接筛选匹配

	private Scheduler scheduler;// 队列管理

	private OwnerBuilderAdapter builderAdapter; // 对象构建

	private Pipeline<Object> pipeline;// 对象信息 管道

	private Configer configer;

	{
		downLoader = new JsoupDownloader();
		pipeline = new DeafultPipeLine<>();
		scheduler = new QueueScheduler();
		configer = new Configer();
	}

	public Sprider init(Class<?> clazz, String... urls) {
		return init(clazz, pipeline, urls);
	}

	public Sprider init(Class<?> clazz, Pipeline<Object> pipeline, String... urls) {
		Builder builder = new OwnerTreeBuilder();
		builderAdapter = new OwnerBuilderAdapter(clazz, builder);
		linksFilter = new LinksFilter(builderAdapter);
		this.pipeline = pipeline;
		scheduler.push(urls);
		return this;
	}

	public Configer configer() {
		return configer;
	}

	public void run() {
		if (builderAdapter == null) {
			throw new NullPointerException();
		}
		this.download();
	}

	public void run(FetchType fetchType) {
		if (builderAdapter == null || fetchType == null) {
			throw new NullPointerException();
		}
		this.download(fetchType);
	}

	private void download() {
		while (scheduler.size() > 0) {
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

	private void download(FetchType fetchType) {
		switch (fetchType) {
		case Eager:
			this.download();
			break;
		default:
			while (scheduler.size() > 0) {
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

	public class Configer {

		private Sprider that = Sprider.this;

		private Cache cache;

		public Configer setDownLoader(DownLoader downLoader) {
			that.downLoader = downLoader;
			return this;
		}

		public Configer setScheduler(Scheduler scheduler) {
			that.scheduler = scheduler;
			that.scheduler.setCache(cache);
			return this;
		}

		public Configer setCache(Cache cache) {
			this.cache = cache;
			that.scheduler.setCache(cache);
			return this;
		}

		public Configer setBuilder(Builder builder) {
			that.builderAdapter.setBuilder(builder);
			return this;
		}

		@SuppressWarnings("unchecked")
		public Configer setPipeline(Pipeline<? extends Object> pipeline) {
			that.pipeline = (Pipeline<Object>) pipeline;
			return this;
		}

		public Configer addRequest(String... requests) {
			that.scheduler.push(requests);
			return this;
		}

		public void run() {
			that.run();
		}

		public void run(FetchType fetchType) {
			that.run(fetchType);
		}
	}

	public enum FetchType {
		Eager, Lazy
	}

}
