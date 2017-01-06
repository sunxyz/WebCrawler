package cn.sunxyz.webcrawler;

import cn.sunxyz.webcrawler.builder.Builder;
import cn.sunxyz.webcrawler.builder.OwnerBuilderAdapter;
import cn.sunxyz.webcrawler.builder.OwnerTreeBuilder;
import cn.sunxyz.webcrawler.download.DownLoader;
import cn.sunxyz.webcrawler.download.JsoupDownloader;
import cn.sunxyz.webcrawler.parser.link.LinksFilter;
import cn.sunxyz.webcrawler.pipeline.Pipeline;
import cn.sunxyz.webcrawler.scheduler.QueueScheduler;
import cn.sunxyz.webcrawler.scheduler.Scheduler;
import cn.sunxyz.webcrawler.scheduler.cache.Cache;

public abstract class AbstratSprider {

	static int sleep;

	static FetchType fetchType;

	static Scheduler scheduler;// 队列管理

	protected DownLoader downLoader;// 下载器

	protected LinksFilter linksFilter;// 链接筛选匹配

	protected OwnerBuilderAdapter builderAdapter; // 对象构建

	protected Pipeline<Object> pipeline;// 对象信息 管道

	private Configer configer;

	static {
		sleep = 1000;
		fetchType = FetchType.Eager;
		scheduler = new QueueScheduler();
	}

	{
		downLoader = new JsoupDownloader();
		configer = new Configer();
	}

	abstract void download(FetchType fetchType);

	public Configer configer() {
		return this.configer;
	}

	protected void start(FetchType fetchType) {
		if (builderAdapter == null || fetchType == null) {
			throw new NullPointerException();
		}
		this.download(fetchType);
	}

	protected void init(Class<?> clazz, Pipeline<Object> pipeline, String... urls) {
		Builder builder = new OwnerTreeBuilder();
		this.builderAdapter = new OwnerBuilderAdapter(clazz, builder);
		this.linksFilter = new LinksFilter(builderAdapter);
		this.pipeline = pipeline;
		scheduler.push(urls);
	}

	public class Configer {

		private AbstratSprider that = AbstratSprider.this;
		// TODO cache稍后做
		private Cache cache;

		public Configer setDownLoader(DownLoader downLoader) {
			that.downLoader = downLoader;
			return this;
		}

		public Configer setScheduler(Scheduler scheduler) {
			AbstratSprider.scheduler = scheduler;
			scheduler.setCache(cache);
			return this;
		}

		public Configer setCache(Cache cache) {
			this.cache = cache;
			AbstratSprider.scheduler.setCache(cache);
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
			AbstratSprider.scheduler.push(requests);
			return this;
		}

	}

	public enum FetchType {
		Eager, Lazy
	}
}
