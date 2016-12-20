package cn.sunxyz.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import cn.sunxyz.bean.bean.JavaBean;
import cn.sunxyz.bean.bean.JavaBeanField;
import cn.sunxyz.bean.setter.MapSetter;
import cn.sunxyz.bean.setter.Setter;
import cn.sunxyz.bean.utils.JavaBeanUtil;

public class JavaBeanManager {

	private JavaBeanManager() {

	}

	private Setter setter;

	private Class<?> clazz;

	private Annotation[] classAnnotation;

	private JavaBeanField[] beanFields;

	public static JavaBeanManager create() {
		return new JavaBeanManager();
	}

	public JavaBeanManager init(Class<?> clazz, Object... args) {
		this.init(clazz, new MapSetter(), args);
		return this;
	}

	public JavaBeanManager init(Class<?> clazz, Setter setter, Object... args) {
		this.clazz = clazz;
		this.setter = setter;
		this.initClassInfo();
		if (args.length > 0) {
			this.setter(args);
		}
		return this;
	}

	private void initClassInfo() {
		List<Field> fields = JavaBeanUtil.getAllField(clazz);
		int size = fields.size();
		beanFields = new JavaBeanField[size];
		for (int i = 0; i < size; i++) {
			Field field = fields.get(i);
			Annotation[] annotations = field.getAnnotations();
			beanFields[i] = new JavaBeanField(field, annotations);
		}
		classAnnotation = clazz.getAnnotations();

	}

	public Object setter(Object... args) {
		JavaBean bean = crateBean();
		setter.setter(bean, args);
		return bean.getOwner();
	}

	private JavaBean crateBean() {
		Object owner = JavaBeanUtil.newInstance(clazz);
		return new JavaBean(owner, beanFields, classAnnotation);
	}

	public JavaBean getJavaBean() {
		return crateBean();
	}

}
