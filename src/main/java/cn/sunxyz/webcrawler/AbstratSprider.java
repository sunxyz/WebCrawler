package cn.sunxyz.webcrawler;

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

public abstract class AbstratSprider {

	protected static int sleep = 1000;

	protected static FetchType fetchType;

	protected static Scheduler scheduler;// 队列管理

	protected static DownLoader downLoader;// 下载器

	protected static LinksFilter linksFilter;// 链接筛选匹配

	protected static OwnerBuilderAdapter builderAdapter; // 对象构建

	protected static Pipeline<Object> pipeline;// 对象信息 管道

	protected static Configer configer;

	static {
		scheduler = new QueueScheduler();
		downLoader = new JsoupDownloader();
		pipeline = new DeafultPipeLine<>();
		configer = new Configer();
		fetchType = FetchType.Eager;
	}

	abstract void download(FetchType fetchType);

	public void start(FetchType fetchType) {
		if (builderAdapter == null || fetchType == null) {
			throw new NullPointerException();
		}
		this.download(fetchType);
	}

	public Configer configer() {
		return configer;
	}

	protected static void init(Class<?> clazz, Pipeline<Object> pipeline, String... urls) {
		Builder builder = new OwnerTreeBuilder();
		builderAdapter = new OwnerBuilderAdapter(clazz, builder);
		linksFilter = new LinksFilter(builderAdapter);
		AbstratSprider.pipeline = pipeline;
		scheduler.push(urls);
	}

	public static class Configer {

		private Cache cache;

		public Configer setDownLoader(DownLoader downLoader) {
			AbstratSprider.downLoader = downLoader;
			return this;
		}

		public Configer setScheduler(Scheduler scheduler) {
			AbstratSprider.scheduler = scheduler;
			AbstratSprider.scheduler.setCache(cache);
			return this;
		}

		public Configer setCache(Cache cache) {
			this.cache = cache;
			AbstratSprider.scheduler.setCache(cache);
			return this;
		}

		public Configer setBuilder(Builder builder) {
			AbstratSprider.builderAdapter.setBuilder(builder);
			return this;
		}

		@SuppressWarnings("unchecked")
		public Configer setPipeline(Pipeline<? extends Object> pipeline) {
			AbstratSprider.pipeline = (Pipeline<Object>) pipeline;
			return this;
		}

		public Configer setFetchType(FetchType fetchType) {
			AbstratSprider.fetchType = fetchType;
			return this;
		}

		public Configer addRequest(String... requests) {
			AbstratSprider.scheduler.push(requests);
			return this;
		}

	}

	public enum FetchType {
		Eager, Lazy
	}
}
