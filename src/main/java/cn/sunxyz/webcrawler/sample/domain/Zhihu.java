package cn.sunxyz.webcrawler.sample.domain;

import cn.sunxyz.webcrawler.builder.annotation.ExtractBy;
import cn.sunxyz.webcrawler.builder.annotation.ExtractElement;
import cn.sunxyz.webcrawler.builder.annotation.HelpLink;
import cn.sunxyz.webcrawler.builder.annotation.TargetLink;

@TargetLink(value = "(https://www.zhihu.com/question/\\d*)", group = 1)
@HelpLink("https://www.zhihu.com/topic/\\w+")
public class Zhihu {

	@ExtractBy("//title/text()")
	private String title;

	@ExtractBy("//[@id='zh-question-detail']/[@class='zm-editable-content']/text()")
	private String question;

	@ExtractElement
	private ZhihuAnswers answers;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public ZhihuAnswers getAnswers() {
		return answers;
	}

	public void setAnswers(ZhihuAnswers answers) {
		this.answers = answers;
	}

	
	
}
