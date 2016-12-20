package cn.sunxyz.bean.sample;

import java.util.HashMap;
import java.util.Map;

import cn.sunxyz.bean.JavaBeanManager;


public class SampleDemo {
	
	public static void main(String[] args) {
		Map<String,Object> field_val = new HashMap<>();
		field_val.put("address", "address");
		JavaBeanManager.create().init(Child.class,field_val);
	}
}
