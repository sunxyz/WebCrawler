package cn.sunxyz.webcrawler.download;

import cn.sunxyz.webcrawler.Page;
import cn.sunxyz.webcrawler.Request;

public interface DownLoader {
	
	Page dowloader(Request request);
	
	Page dowloader(String request);

}
