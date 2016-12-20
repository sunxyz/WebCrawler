package cn.sunxyz.webcrawler.download;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cn.sunxyz.webcrawler.Page;
import cn.sunxyz.webcrawler.Request;


public class JsoupDownloader implements DownLoader {

	private static Integer timeout = 5000;

	public Document downloadDocument(String url) throws IOException  {
		return Jsoup.connect(url).timeout(timeout).get();
	}

	@Override
	public Page dowloader(Request request) {
		Page page = new Page().setRequest(request);
		try {
			Document document = this.downloadDocument(request.getUrl());
			page.setDocument(document);
		} catch (IOException e) {
			page.setStatus(false);
		}
		return page;
	}

	@Override
	public Page dowloader(String request) {
		return dowloader(new Request(request));
	}

}
