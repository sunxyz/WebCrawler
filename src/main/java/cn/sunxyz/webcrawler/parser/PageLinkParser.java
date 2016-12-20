package cn.sunxyz.webcrawler.parser;

import java.util.Set;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;

import cn.sunxyz.webcrawler.Page;

public class PageLinkParser implements LinkParser {
	
/*	private static final String HREF_MATCH = "//a/@href";*/
	
	@Override
	public Set<String> getLinks(Page page){
		Document document = page.getDocument();
		return this.getHrefList(document);
	}
	/*
	private Set<Request> getRequest(Document document){
		Set<String> hrefs = getHrefList(document);
		return hrefs.parallelStream().map(url -> new Request(url)).collect(Collectors.toSet());
	}*/

/*	private List<String> getXsoupList(String xPath, Document document) {
		return Xsoup.compile(HREF_MATCH).evaluate(document).list();
	}*/
	
	private Set<String> getHrefList(Document document) {
		return document.select("a").parallelStream().map(link -> link.attr("abs:href")).collect(Collectors.toSet());
	}

}
