package cn.sunxyz.webcrawler.queue.component;

public interface LinkSet<R>{

	void push(R request);

	boolean hasExisted(R request);

	void clear();

	int size();
}
