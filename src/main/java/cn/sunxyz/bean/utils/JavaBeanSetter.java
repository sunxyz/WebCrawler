package cn.sunxyz.bean.utils;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * javaBean 工具 设值方法工具
 * 
 * @author 杨瑞东
 * @date 2016年12月17日 下午2:33:42
 * @see JavaBeanUtil
 *
 */
public final class JavaBeanSetter {

	private static Logger logger = LoggerFactory.getLogger(JavaBeanSetter.class);
	
	private JavaBeanSetter() {

	}

	public static void setMethod(Object owner, Field field, List<?> values) {
		if (!values.isEmpty()) {
			Class<?> fClazz = field.getType();
			// TODO 只实现了基本的容器
			Class<?> gtClazz = getClass(field.getGenericType(), 0);
			if (fClazz == Set.class || fClazz == Collection.class) {
				Set<?> set = values.stream().map(item -> getValue(gtClazz, item)).collect(Collectors.toSet());
				JavaBeanUtil.setMethod(owner, field, set);
			} else if (fClazz == List.class ) {
				List<?> list = values.stream().map(item -> getValue(gtClazz, item)).collect(Collectors.toList());
				JavaBeanUtil.setMethod(owner, field, list);
			} else if (fClazz == Map.class) {
				// TODO 暂时不考虑实现
			} else {
				JavaBeanUtil.setMethod(owner, field, getValue(field, values.get(0)));
			}
		}
	}

	// 非集合数据
	private static Object getValue(Field field, Object obj) {
		Class<?> fieldClazz = field.getType();
		return getValue(fieldClazz, obj);
	}

	private static Object getValue(Class<?> fieldClazz, Object obj) {
		FieldType fieldType1 = FieldType.getType(fieldClazz);
		if (fieldType1 == null) {
			return obj;
		} else {
			try {
				String item = obj.toString().trim();
				switch (fieldType1) {
				case BYTE:
					return Byte.valueOf(item);
				case INTEGER:
					return Integer.valueOf(item);
				case SHORT:
					return Short.valueOf(item);
				case LONG:
					return Long.valueOf(item);
				case BOOLEAN:
					return Boolean.valueOf(item);
				case CHAR:
					return item.charAt(0);
				case FLOAT:
					return Float.valueOf(item);
				case DOUBLE:
					return Double.valueOf(item);
				case STRING:
					return item;
				default:
					return obj;
				}
			} catch (RuntimeException e) {
				//此处不需要做处理
				logger.error(e.getLocalizedMessage());
				return null;
			}
			
		}

	}

	/**
	 * 
	 * 得到泛型类对象
	 * 
	 * @param type
	 * @param i
	 * @return Class
	 */
	@SuppressWarnings("rawtypes")
	protected static Class getClass(Type type, int i) {
		if (type instanceof ParameterizedType) { // 处理泛型类型
			return getGenericClass((ParameterizedType) type, i);
		} else if (type instanceof TypeVariable) {
			return (Class) getClass(((TypeVariable) type).getBounds()[0], 0); // 处理泛型擦拭对象
		} else {// class本身也是type，强制转型
			return (Class) type;
		}
	}

	@SuppressWarnings("rawtypes")
	private static Class getGenericClass(ParameterizedType parameterizedType, int i) {
		Object genericClass = parameterizedType.getActualTypeArguments()[i];
		if (genericClass instanceof ParameterizedType) { // 处理多级泛型
			return (Class) ((ParameterizedType) genericClass).getRawType();
		} else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
			return (Class) ((GenericArrayType) genericClass).getGenericComponentType();
		} else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象
			return (Class) getClass(((TypeVariable) genericClass).getBounds()[0], 0);
		} else {
			return (Class) genericClass;
		}
	}

	public enum FieldType {
		BYTE(byte.class, Byte.class), INTEGER(int.class, Integer.class), SHORT(short.class, Short.class), LONG(
				long.class, Long.class), BOOLEAN(boolean.class, Boolean.class), CHAR(char.class,
						Character.class), FLOAT(float.class,
								Float.class), DOUBLE(double.class, Double.class), STRING(String.class, String.class);

		private Class<?> baseClazz;
		private Class<?> packClazz;

		FieldType(Class<?> baseClazz, Class<?> packClazz) {
			this.baseClazz = baseClazz;
			this.packClazz = packClazz;
		}

		public Class<?> getBaseClazz() {
			return baseClazz;
		}

		public Class<?> getPackClazz() {
			return packClazz;
		}

		public static FieldType getType(Class<?> clazz) {
			FieldType fieldTypes[] = FieldType.values();
			for (FieldType fieldType : fieldTypes) {
				if (fieldType.getBaseClazz() == clazz || fieldType.getPackClazz() == clazz) {
					return fieldType;
				}
			}
			return null;
		}
	}

}
