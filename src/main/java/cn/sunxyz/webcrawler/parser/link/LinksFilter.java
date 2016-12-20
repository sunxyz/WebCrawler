package cn.sunxyz.webcrawler.parser.link;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sunxyz.bean.bean.JavaBean;
import cn.sunxyz.webcrawler.Page;
import cn.sunxyz.webcrawler.Request;
import cn.sunxyz.webcrawler.Request.RequestLinkType;
import cn.sunxyz.webcrawler.builder.OwnerBuilderAdapter;
import cn.sunxyz.webcrawler.builder.annotation.HelpLink;
import cn.sunxyz.webcrawler.builder.annotation.TargetLink;
import cn.sunxyz.webcrawler.parser.LinkParser;
import cn.sunxyz.webcrawler.parser.PageLinkParser;

public class LinksFilter {

	private LinkParser linkParser;
	private boolean islinkAnn = false;
	private Set<Request> requests = new HashSet<>();
	// TODO 可以设置默认值
	private final String regex = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://\\w*?)";
	private Pattern targetLinkPattern = Pattern.compile(regex);
	private int targetLinkGroup = 0;
	private Pattern helpLinkPattern = Pattern.compile(regex);
	private int helpLinkGroup = 0;

	{
		linkParser = new PageLinkParser();
	}

	public LinksFilter(OwnerBuilderAdapter builderAdapter) {
		this.initRegex(builderAdapter);
	}

	private void initRegex(OwnerBuilderAdapter builderAdapter) {
		JavaBean bean = builderAdapter.getNewBean();
		Annotation[] annotations = bean.getClassAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof HelpLink) {
				HelpLink helpLink = (HelpLink) annotation;
				helpLinkPattern = Pattern.compile(helpLink.value());
				helpLinkGroup = helpLink.group();
				islinkAnn = true;
			} else if (annotation instanceof TargetLink) {
				TargetLink targetLink = (TargetLink) annotation;
				targetLinkPattern = Pattern.compile(targetLink.value());
				targetLinkGroup = targetLink.group();
				islinkAnn = true;
			}
		}
	}

	public Set<Request> getRequests(Page page) {
		if (!islinkAnn) {
			throw new NullPointerException("@TargetLink or @HelpLink is null");
		}
		return this.getRequests(linkParser.getLinks(page));
	}

	private Set<Request> getRequests(Set<String> links) {
		requests.clear();
		for (String link : links) {
			try {
				Matcher tm = targetLinkPattern.matcher(link);
				if (tm.find()) {
					requests.add(new Request(tm.group(targetLinkGroup), RequestLinkType.TARGET));
				}
				Matcher hm = helpLinkPattern.matcher(link);
				if (hm.find()) {
					requests.add(new Request(tm.group(helpLinkGroup), RequestLinkType.HELP));
				}
			} catch (IllegalStateException e) {
				//logger.error("regex group : {}", e.getMessage());
				//此处不需要抛出异常
			}
		}
		return requests;
	}

}
