package cn.sunxyz.bean.bean;

import java.lang.annotation.Annotation;

public class JavaBean {

	private Object owner;

	private JavaBeanField[] beanFields;

	private Annotation[] classAnnotations;

	public JavaBean() {
		super();
	}

	public JavaBean(Object owner, JavaBeanField[] beanFields) {
		super();
		this.owner = owner;
		this.beanFields = beanFields;
	}

	public JavaBean(Object owner, JavaBeanField[] beanFields, Annotation[] classAnnotations) {
		this(owner, beanFields);
		this.classAnnotations = classAnnotations;
	}

	public Object getOwner() {
		return owner;
	}

	public void setOwner(Object owner) {
		this.owner = owner;
	}

	public JavaBeanField[] getBeanFields() {
		return beanFields;
	}

	public void setBeanFields(JavaBeanField[] beanFields) {
		this.beanFields = beanFields;
	}

	public Annotation[] getClassAnnotations() {
		return classAnnotations;
	}

	public void setClassAnnotations(Annotation[] classAnnotations) {
		this.classAnnotations = classAnnotations;
	}

}
