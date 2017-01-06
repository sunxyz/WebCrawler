package cn.sunxyz.webcrawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.sunxyz.webcrawler.AbstratSprider.FetchType;
import cn.sunxyz.webcrawler.builder.Builder;
import cn.sunxyz.webcrawler.download.DownLoader;
import cn.sunxyz.webcrawler.pipeline.Pipeline;
import cn.sunxyz.webcrawler.scheduler.Scheduler;
import cn.sunxyz.webcrawler.scheduler.cache.Cache;

public final class SpriderManager {

	private static SpriderManager manager;

	private static Configer configer;

	private static int nThreads = 10;

	private static int task = 10;

	private static ExecutorService executor;

	private static WSprider[] wSpriders;

	static {
		manager = new SpriderManager();
		configer = new Configer();
	}

	private SpriderManager() {

	}

	public static SpriderManager create(Class<?> clazz, String... urls) {
		// 创建任务
		WSprider.scheduler.push(urls);
		// 创建对象实例
		wSpriders = new WSprider[task];
		for (int i = 0; i < task; i++) {
			wSpriders[i] = new WSprider(clazz);
		}
		return manager;
	}

	public Configer configer() {
		return configer;
	}

	@SuppressWarnings("static-access")
	public void run() {
		// 执行任务
		executor = Executors.newFixedThreadPool(nThreads);
		for (int i = 0; i < task; i++) {
			WSprider wSprider = wSpriders[i];
			try {
				Thread.currentThread().sleep(WSprider.sleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			executor.execute(wSprider);
		}
		executor.shutdown();
	}

	public void run(FetchType fetchType) {
		configer().setFetchType(fetchType);
		run();
	}

	public static class Configer {

		public Configer setThread(int size) {
			SpriderManager.nThreads = size;
			return this;
		}

		public Configer setTask(int task) {
			SpriderManager.task = task;
			return this;
		}

		public Configer setSleep(int sleep) {
			WSprider.sleep = sleep;
			return this;
		}

		public void run() {
			manager.run();
		}

		public void run(FetchType fetchType) {
			manager.run(fetchType);
		}

		private Cache cache;

		public Configer setScheduler(Scheduler scheduler) {
			WSprider.scheduler = scheduler;
			WSprider.scheduler.setCache(cache);
			return this;
		}

		public Configer setCache(Cache cache) {
			this.cache = cache;
			WSprider.scheduler.setCache(cache);
			return this;
		}

		public Configer setFetchType(FetchType fetchType) {
			WSprider.fetchType = fetchType;
			return this;
		}

		public Configer addRequest(String... requests) {
			WSprider.scheduler.push(requests);
			return this;
		}

		public Configer setDownLoader(Class<? extends DownLoader> downLoaderClazz) {
			for (int i = 0; i < task; i++) {
				DownLoader downLoader = null;
				try {
					downLoader = (DownLoader) downLoaderClazz.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				wSpriders[i].configer().setDownLoader(downLoader);
			}

			return this;
		}

		public Configer setBuilder(Class<? extends Builder> builderClazz) {
			for (int i = 0; i < task; i++) {
				Builder builder = null;
				try {
					builder = (Builder) builderClazz.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				wSpriders[i].configer().setBuilder(builder);
			}
			return this;
		}

		public <T> Configer setPipeline(Class<? extends Pipeline<T>> pipelineClazz) {
			for (int i = 0; i < task; i++) {
				Pipeline<T> pipeline = null;
				try {
					pipeline = (Pipeline<T>) pipelineClazz.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				wSpriders[i].configer().setPipeline(pipeline);
			}
			return this;
		}

	}
}
