package cn.sunxyz.webcrawler.pipeline;

import cn.sunxyz.webcrawler.Site;

public interface Pipeline<T> {
	
	void process(T t, Site site);

}
