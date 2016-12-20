package cn.sunxyz.webcrawler.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;

import cn.sunxyz.bean.bean.JavaBean;
import cn.sunxyz.bean.bean.JavaBeanField;
import cn.sunxyz.bean.utils.JavaBeanSetter;
import cn.sunxyz.bean.utils.JavaBeanUtil;
import cn.sunxyz.webcrawler.Page;
import cn.sunxyz.webcrawler.builder.annotation.ExtractBy;
import cn.sunxyz.webcrawler.builder.annotation.ExtractElement;
import us.codecraft.xsoup.Xsoup;

/**
 * 
 * @see ExtractElement
 * @see ExtractBy
 * @author 杨瑞东
 * @date 2016年12月19日 下午5:12:28 
 * 目前 ExtractElement 注解只能用来注解用户创建的类
 *
 */
public class OwnerTreeBuilder implements Builder {

	private static Map<String, OwnerBuilderAdapter> builders = new ConcurrentHashMap<String, OwnerBuilderAdapter>();

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
				} else if (annotation instanceof ExtractElement) {
					Field field = beanField.getField();
					Class<?> elClazz = field.getType();
					OwnerBuilderAdapter adapter = this.getBuilderAdapter(elClazz);
					Object ownerEl = adapter.buildOwner(page);
					JavaBeanUtil.setMethod(owner, field, ownerEl);
				}
			}
		}
	}

	private OwnerBuilderAdapter getBuilderAdapter(Class<?> clazz) {
		String key = clazz.getName();
		if (builders.containsKey(key)) {
			return builders.get(key);
		} else {
			OwnerBuilderAdapter adapter = new OwnerBuilderAdapter(clazz, this);
			builders.put(key, adapter);
			return adapter;
		}
	}

}
