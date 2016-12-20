package cn.sunxyz.webcrawler.sample.domain;

import java.util.List;

import cn.sunxyz.webcrawler.builder.annotation.ExtractBy;

public class ZhihuAnswers {

	@ExtractBy("//[@class='up']/[@class='count']/text()")
	private List<String> count;

	@ExtractBy(value = "//div[@class='zm-editable-content clearfix']/text()")
	private List<String> answerList;

	public List<String> getCount() {
		return count;
	}

	public void setCount(List<String> count) {
		this.count = count;
	}

	public List<String> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<String> answerList) {
		this.answerList = answerList;
	}

}
