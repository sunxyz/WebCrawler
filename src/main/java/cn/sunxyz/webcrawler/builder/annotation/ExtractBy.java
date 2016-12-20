package cn.sunxyz.webcrawler.builder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtractBy {

	String value();

	ExtractType type() default ExtractType.XSOUP;

	public enum ExtractType {
		XSOUP, SELECT
	}

}
