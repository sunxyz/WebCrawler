package cn.sunxyz.bean.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class JavaBeanField {

	private Field field;

	private Annotation[] annotations;

	public JavaBeanField() {
		super();
	}

	public JavaBeanField(Field field, Annotation[] annotations) {
		super();
		this.field = field;
		this.annotations = annotations;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Annotation[] getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Annotation[] annotations) {
		this.annotations = annotations;
	}

}
