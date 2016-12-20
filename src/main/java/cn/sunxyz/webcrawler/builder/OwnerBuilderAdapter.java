package cn.sunxyz.webcrawler.builder;

import cn.sunxyz.bean.JavaBeanManager;
import cn.sunxyz.bean.bean.JavaBean;
import cn.sunxyz.webcrawler.Page;

public class OwnerBuilderAdapter {
	
	private JavaBeanManager beanManager;
	
	private Builder builder;

	public OwnerBuilderAdapter(Class<?> clazz, Builder builder){
		beanManager = JavaBeanManager.create();
		this.init(clazz, builder);
	}
	
	private void init(Class<?> clazz, Builder builder){
		this.builder = builder;
		beanManager.init(clazz);
	}
	
	public Object buildOwner(Page page){
		JavaBean bean = this.getNewBean();
		builder.buildOwnerByPage(bean, page);
		return bean.getOwner();
	}
	
	public JavaBean getNewBean(){
		return beanManager.getJavaBean();
	}

	public void setBuilder(Builder builder) {
		this.builder = builder;
	}
	
	

}
