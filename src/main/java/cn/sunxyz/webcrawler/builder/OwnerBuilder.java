package cn.sunxyz.webcrawler.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;

import cn.sunxyz.bean.bean.JavaBean;
import cn.sunxyz.bean.bean.JavaBeanField;
import cn.sunxyz.bean.utils.JavaBeanSetter;
import cn.sunxyz.webcrawler.Page;
import cn.sunxyz.webcrawler.builder.annotation.ExtractBy;
import us.codecraft.xsoup.Xsoup;

public class OwnerBuilder implements Builder {


	@Override
	public void buildOwnerByPage(JavaBean bean, Page page) {
		Object owner = bean.getOwner();
		JavaBeanField[] beanFields = bean.getBeanFields();
		Document document = page.getDocument();
		for (JavaBeanField beanField : beanFields) {
			Annotation[] annotations = beanField.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation instanceof ExtractBy) {
					List<String> result = null;
					ExtractBy extractBy = (ExtractBy) annotation;
					String match = extractBy.value();
					switch (extractBy.type()) {
					case XSOUP:
						result = Xsoup.compile(match).evaluate(document).list();
						break;
					case SELECT:
						result = document.select(match).stream().map(e -> e.toString()).collect(Collectors.toList());
						break;
					default:
						break;
					}
					Field field = beanField.getField();
					JavaBeanSetter.setMethod(owner, field, result);
				}
			}
		}
	}

}
