package cn.sunxyz.webcrawler.builder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
* 
* @author 杨瑞东
* @date 2016年12月3日 下午5:02:32
* @see Sprider
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetLink {
	
	String value();
	
	int group() default 0;

}
