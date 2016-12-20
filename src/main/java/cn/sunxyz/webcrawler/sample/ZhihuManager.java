package cn.sunxyz.webcrawler.sample;

import cn.sunxyz.webcrawler.SpriderManager;
import cn.sunxyz.webcrawler.sample.domain.Zhihu;
import cn.sunxyz.webcrawler.sample.pipeline.ZhihuPipeline;

public class ZhihuManager {

	public static void main(String[] args) {
		SpriderManager.create(Zhihu.class, "https://www.zhihu.com/question/29073730").configer()
				.setPipeline(new ZhihuPipeline()).setThread(10).run();
	}

}
