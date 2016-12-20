package cn.sunxyz.webcrawler.sample.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sunxyz.webcrawler.Site;
import cn.sunxyz.webcrawler.pipeline.DeafultPipeLine;
import cn.sunxyz.webcrawler.sample.domain.Zhihu;

public class ZhihuPipeline extends DeafultPipeLine<Zhihu>{

	private static Logger logger = LoggerFactory.getLogger(ZhihuPipeline.class);
	
	@Override
	public void process(Zhihu t, Site site) {
//		logger.debug(JSON.toJSONString(t));
		logger.debug("dowload {}", site.getPage().getRequest().getUrl());
	}

}
