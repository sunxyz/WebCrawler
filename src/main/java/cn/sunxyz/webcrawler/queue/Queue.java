package cn.sunxyz.webcrawler.queue;

public interface Queue<R>{

	void push(R request);

	R pop();

	int size();

}
