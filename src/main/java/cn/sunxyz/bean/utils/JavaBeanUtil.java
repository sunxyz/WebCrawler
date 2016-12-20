package cn.sunxyz.bean.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 杨瑞东
 * @date 2016年12月3日 上午11:43:01
 *
 */
public final class JavaBeanUtil {

	private JavaBeanUtil() {

	}

	private static class Config {
		static final String GETTER = "get";
		static final String SETTER = "set";
	}

	/**
	 * 获取所有字段信息
	 * 
	 * @author: 杨瑞东
	 */
	public static List<Field> getAllField(Class<?> clazz) {
		List<Field> result = new ArrayList<>();
		List<Class<?>> clazzs = getSupperClassList(clazz);
		for (Class<?> _clazz : clazzs) {
			Field[] fields = _clazz.getDeclaredFields();
			for (Field field : fields) {
				result.add(field);
			}
		}
		return result;
	}

	/**
	 * 方法作用域需要共有
	 * 
	 * @author: 杨瑞东
	 */
	@Deprecated
	public static void setMethod(Object owner, String fieldName, Object arg) {
		String methodName = Config.SETTER + UpCase(fieldName);
		invokeMethod(owner, methodName, arg);
	}

	public static void setMethod(Object owner, Field field, Object arg) {
		beanSetMethod(owner, field, arg);
	}

	/**
	 * 方法作用域需要共有
	 * 
	 * @author: 杨瑞东
	 */
	public static Object getMethod(Object owner, String fieldName) {
		String methodName = Config.GETTER + UpCase(fieldName);
		return invokeMethod(owner, methodName);
	}

	public static List<Class<?>> getSupperClassList(Class<?> clazz) {
		List<Class<?>> clazzs = new ArrayList<>();
		getSupperClassList(clazzs, clazz);
		return clazzs;
	}

	public static Object newInstance(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Deprecated
	private static Object invokeMethod(Object owner, String methodName, Object... args) {
		Class<?> ownerClass = owner.getClass();
		Class<?>[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			if (args[i].getClass() == Integer.class) { // 一般的函数都是 int 而不是Integer
				argsClass[i] = int.class;
			} else if (args[i].getClass() == Float.class) {
				argsClass[i] = float.class;
			} else if (args[i].getClass() == Double.class) {
				argsClass[i] = double.class;
			} else {
				argsClass[i] = args[i].getClass();
			}
		}
		try {
			Method method = ownerClass.getMethod(methodName, argsClass);
			return method.invoke(owner, args);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static Object beanSetMethod(Object owner, Field field, Object arg) {
		String methodName = Config.SETTER + UpCase(field.getName());
		Class<?> ownerClass = owner.getClass();
		Class<?> argClass = field.getType();
		try {
			Method method = ownerClass.getMethod(methodName, argClass);
			return method.invoke(owner, arg);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static void getSupperClassList(List<Class<?>> clazzs, Class<?> clazz) {
		if (clazz != null) {
			clazzs.add(clazz);
			if (clazz.getSuperclass() != Object.class) {
				Class<?> clazzParent = clazz.getSuperclass();
				getSupperClassList(clazzs, clazzParent);
			}
		}

	}

	private static String UpCase(String fieldName) {
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
	
}
