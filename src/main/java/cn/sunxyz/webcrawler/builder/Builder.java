package cn.sunxyz.webcrawler.builder;

import cn.sunxyz.bean.bean.JavaBean;
import cn.sunxyz.webcrawler.Page;

public interface Builder {

	void buildOwnerByPage(JavaBean javaBean, Page page);
	
}
