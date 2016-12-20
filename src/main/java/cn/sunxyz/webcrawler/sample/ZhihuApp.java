package cn.sunxyz.webcrawler.sample;

import cn.sunxyz.webcrawler.Sprider;
import cn.sunxyz.webcrawler.Sprider.FetchType;
import cn.sunxyz.webcrawler.sample.domain.Zhihu;
import cn.sunxyz.webcrawler.sample.pipeline.ZhihuPipeline;
import cn.sunxyz.webcrawler.sample.scheduler.ZhihuScheduler;

public class ZhihuApp {

	public static void main(String[] args) {
		new ZhihuApp().fetchLazyForUrls();
	}

	public void fetchLazyForScheduler() {
		new Sprider().init(Zhihu.class).configer().setPipeline(new ZhihuPipeline()).setScheduler(new ZhihuScheduler())
				.run(FetchType.Lazy);
	}

	public void fetchLazyForUrls() {
		String[] urls = new String[10];
		for (int i = 0; i < urls.length; i++) {
			urls[i] = "https://www.zhihu.com/question/" + (29073730 + i);
		}
		new Sprider().init(Zhihu.class, urls).configer().setPipeline(new ZhihuPipeline()).run(FetchType.Lazy);
	}

}
