package cn.sunxyz.webcrawler;

import org.jsoup.nodes.Document;


public class Page {
	
	private Request request;

	private Document document;
	
	private Boolean status;
	
	{
		status = true;
	}
	
	public Request getRequest() {
		return request;
	}

	public Page setRequest(Request request) {
		this.request = request;
		return this;
	}

	public Document getDocument() {
		return document;
	}

	public Page setDocument(Document document) {
		this.document = document;
		return this;
	}

	public Boolean getStatus() {
		return status;
	}

	public Page setStatus(Boolean status) {
		this.status = status;
		return this;
	}
	
	
}
