package cn.sunxyz.bean.setter;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.sunxyz.bean.bean.JavaBean;
import cn.sunxyz.bean.bean.JavaBeanField;
import cn.sunxyz.bean.utils.JavaBeanUtil;

public class MapSetter implements Setter {

	private static Logger logger = LoggerFactory.getLogger(MapSetter.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setter(JavaBean bean, Object... args) {
		Object owner = bean.getOwner();
		for (Object object : args) {
			if (object instanceof Map) {
				JavaBeanField[] fields = bean.getBeanFields();
				JavaBeanMap beanMap = new JavaBeanMap();
				for (JavaBeanField javaBeanField : fields) {
					beanMap.addField(javaBeanField.getField().getName(), javaBeanField);
				}
				Map<String,Object> map = (Map)object;
				for(String key: map.keySet()){
					Object val = map.get(key);
					if(key.equals(beanMap.getField(key).getField().getName())) {
						JavaBeanUtil.setMethod(owner, beanMap.getField(key).getField(), val);
					}
				}
			}
		}
		logger.debug(JSON.toJSONString(owner));
	}

	class JavaBeanMap {

		private Map<String, JavaBeanField> fieldMap;

		public JavaBeanField getField(String key) {
			return fieldMap.get(key);
		}

		public void setFieldMap(Map<String, JavaBeanField> fieldMap) {
			this.fieldMap = fieldMap;
		}

		public void addField(String fieldName, JavaBeanField javaBeanField) {
			if (fieldMap == null)
				fieldMap = new HashMap<>();
			fieldMap.put(fieldName, javaBeanField);
		}

	}

}
