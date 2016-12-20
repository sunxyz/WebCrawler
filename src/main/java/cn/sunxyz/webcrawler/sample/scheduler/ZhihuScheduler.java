package cn.sunxyz.webcrawler.sample.scheduler;

import cn.sunxyz.webcrawler.Request;

public class ZhihuScheduler extends BaseScheduler {

	private int i = 0;
	
	@Override
	public Request pop() {
		i++;
		if(i>10){
			this.setSize(0);
		}
		return new Request("https://www.zhihu.com/question/"+(29073730+i));
	}

}
