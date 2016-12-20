package cn.sunxyz.webcrawler;

import java.util.Set;

public class Site {

	private Page page;

	private Set<Request> requests;

	public Site() {
		super();
	}

	public Site(Page page) {
		super();
		this.page = page;
	}

	public Site(Page page, Set<Request> requests) {
		super();
		this.page = page;
		this.requests = requests;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Set<Request> getRequests() {
		return requests;
	}

	public void setRequests(Set<Request> requests) {
		this.requests = requests;
	}

}
