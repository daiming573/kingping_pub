package com.common.reflect;

import com.common.util.date.DateFormat;
import com.common.util.date.DateUtil;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class RReflectUtils{

   private RReflectUtils(){
   }

   public static boolean isContainsMethod(Class<?> clz,
                                          Method m){
      if(clz == null || m == null){
         return false;
      }
      try{
         clz.getDeclaredMethod(m.getName(), m.getParameterTypes());
         return true;
      }catch(NoSuchMethodException e){
         return false;
      }
   }

   public static Object invoke(Object target,
                               String methodName){
      return invoke(target, methodName, null);
   }

   public static Object invoke(Object target,
                               String methodName,
                               Object[] paramValues){
      Class<?>[] parameterTypes = getClassArray(paramValues);
      return invoke(target, methodName, parameterTypes, paramValues);
   }

   public static Object invoke(Object target,
                               String methodName,
                               Class<?>[] paramTypes,
                               Object[] paramValues){
      try{
         Class<?> clazz = target.getClass();
         Method method = clazz.getDeclaredMethod(methodName, paramTypes);
         return invoke(target, method, paramValues);
      }catch(NoSuchMethodException e){
         throw new RuntimeException(e);
      }
   }

   public static Object invoke(Object target,
                               Method method,
                               Object[] paramValues){
      boolean accessible = method.isAccessible();
      try{
         method.setAccessible(true);
         if(Modifier.isStatic(method.getModifiers())){
            return method.invoke(null, paramValues);
         }else{
            return method.invoke(target, paramValues);
         }
      }catch(IllegalAccessException e){
         throw new RuntimeException(e);
      }catch(InvocationTargetException e){
         throw new RuntimeException(e);
      }finally{
         method.setAccessible(accessible);
      }
   }

   public static boolean hasMethod(Object obj,
                                   String methodName,
                                   Class<?>[] paramTypes){
      Class<?> clazz = obj.getClass();
      try{
         clazz.getDeclaredMethod(methodName, paramTypes);
         return true;
      }catch(NoSuchMethodException e){
         return false;
      }
   }

   public static Object invokeStatic(Class<?> clazz,
                                     String methodName,
                                     Object[] paramValues){
      try{
         Class<?>[] parameterTypes = getClassArray(paramValues);
         Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
         return invokeStatic(method, paramValues);
      }catch(NoSuchMethodException e){
         throw new RuntimeException(e);
      }catch(SecurityException e){
         throw new RuntimeException(e);
      }
   }

   public static Object invokeStatic(Class<?> clazz,
                                     String methodName,
                                     Class<?>[] paramTypes,
                                     Object[] paramValues){
      try{
         Method method = clazz.getDeclaredMethod(methodName, paramTypes);
         return invokeStatic(method, paramValues);
      }catch(NoSuchMethodException e){
         throw new RuntimeException(e);
      }catch(SecurityException e){
         throw new RuntimeException(e);
      }
   }

   public static Object invokeStatic(Method method,
                                     Object[] paramValues){
      boolean accessible = method.isAccessible();
      try{
         method.setAccessible(true);
         if(Modifier.isStatic(method.getModifiers())){
            return method.invoke(null, paramValues);
         }else{
            throw new RuntimeException("can not run none static class without object");
         }
      }catch(IllegalAccessException e){
         throw new RuntimeException(e);
      }catch(InvocationTargetException e){
         throw new RuntimeException(e);
      }finally{
         method.setAccessible(accessible);
      }
   }

   public static boolean hasStaticMethod(Class<?> clazz,
                                         String methodName,
                                         Class<?>[] paramTypes){
      try{
         clazz.getDeclaredMethod(methodName, paramTypes);
         return true;
      }catch(NoSuchMethodException e){
         return false;
      }
   }

   public static Class<?>[] getClassArray(Object[] objects){
      if(objects == null){
         return null;
      }
      int arguments = objects.length;
      Class<?>[] objectTypes = new Class[arguments];
      for(int i = 0; i < arguments; i++){
         objectTypes[i] = objects[i].getClass();
      }
      return objectTypes;
   }

   public static Object getStaticField(Class<?> clazz,
                                       String fieldName){
      try{
         Field f = clazz.getDeclaredField(fieldName);
         f.setAccessible(true);
         return f.get(null);
      }catch(Exception e){
         throw new RuntimeException(e);
      }
   }

   public static void setStaticField(Class<?> clazz,
                                     String fieldName,
                                     Object value){
      try{
         Field f = clazz.getDeclaredField(fieldName);
         f.setAccessible(true);
         f.set(null, value);
      }catch(Exception e){
         throw new RuntimeException(e);
      }
   }

   /**
    * 获取对象字段值
    * @param instance   对象
    * @param fieldName  对象字段
    * @return
    */
   public static Object getInstanceField(Object instance,
                                         String fieldName){
      try{
         Field f = instance.getClass().getDeclaredField(fieldName);
         f.setAccessible(true);
         return f.get(instance);
      }catch(Exception e){
         throw new RuntimeException(e);
      }
   }

   /**
    * 实例对象中的字段名称及值
    * @param instance   对象
    * @param fieldName  对象字段名称
    * @param value      对象字段值
    */
   public static void setInstanceField(Object instance,
                                       String fieldName,
                                       Object value){
      try{
         Field f = instance.getClass().getDeclaredField(fieldName);
         f.setAccessible(true);
         if(null != value){
            f.set(instance, getValueByType(value.toString(), f.getGenericType()));
         }
      }catch(Exception e){
      }
   }

   /**
    * 实例对象中的字段名称及值
    * @param instance   对象
    * @param fieldName  对象字段名称
    * @param value      对象字段值为数组
    */
   public static void setInstanceField(Object instance,
                                       String fieldName,
                                       Object[] value){
      try{
         Field f = instance.getClass().getDeclaredField(fieldName);
         f.setAccessible(true);
         if(null != value[0]){
            f.set(instance, getValueByType(value[0].toString(), f.getGenericType()));
         }
      }catch(Exception e){
      }
   }

   /**
    * 获取方法注解
    * @param method
    * @param AnnotationClass
    * @return
    */
   @SuppressWarnings("rawtypes")
   public static Annotation getMethodAnnotation(Method method,
                                                Class AnnotationClass){
      Annotation[] annotations = method.getAnnotations();
      for(Annotation annotation : annotations){
         if(AnnotationClass.getSimpleName().equalsIgnoreCase(annotation.annotationType().getSimpleName())){
            return annotation;
         }
      }
      return null;
   }

   /**
    * 对象转MAP
    * @param object 对象
    * @return Map<String, Object>
    */
   public static Map<String, Object> getFieldMapForClass(Object object){
      Map<String, Object> parameterMap = null;
      try{
         parameterMap = new HashMap<String, Object>();
         Field[] fields = object.getClass().getDeclaredFields();
         for(Field field : fields){
            populateFieldMap(field, object, parameterMap);
         }
         Field[] parentFields = object.getClass().getSuperclass().getDeclaredFields();
         for(Field parentField : parentFields){
            populateFieldMap(parentField, object, parameterMap);
         }
      }catch(Exception e){
         throw new RuntimeException(e);
      }
      return parameterMap;
   }

  /**
    * Map转对象
    * @param target 对象
    * @param map    MAP
    * @return Object
    */
   public static Object getObjectForMap(Object target,
                                        Map<String, Object> map){
      try{
         Field[] fields = target.getClass().getDeclaredFields();
         for(Field field : fields){
            if(map.get(field.getName()) instanceof Object[]) {
               setInstanceField(target, field.getName(), (Object[]) map.get(field.getName()));
            } else {
               setInstanceField(target, field.getName(), map.get(field.getName()));
            }
            
         }
      }catch(Exception e){
         throw new RuntimeException(e);
      }
      return target;
   }
   
   /**
    * Map转对象
    * @param target 对象
    * @param map    MAP
    * @return Object
    */
   public static Object getObjectListForMap(Object target,
                                        Map<String, String[]> map){
      try{
         Field[] fields = target.getClass().getDeclaredFields();
         for(Field field : fields){
            if(map.get(field.getName()) instanceof Object[]) {
               setInstanceField(target, field.getName(), map.get(field.getName()));
            } else {
               setInstanceField(target, field.getName(), map.get(field.getName()));
            }
         }
      }catch(Exception e){
         throw new RuntimeException(e);
      }
      return target;
   }

   
   /**
    * 实体转实体，由实体先转MAP对象，再由MAP对象键值转成对象
    * @param target     新对象
    * @param object     原先对象
    * @return  Object
    */
   public static Object getObjectForObject(Object target,
                                           Object object){
      try{
         Field[] fields = object.getClass().getDeclaredFields();
         Map<String, Object> map = getFieldMapForClass(object);
         for(Field field : fields){
            setInstanceField(target, field.getName(), map.get(field.getName()));
         }
      }catch(Exception e){
         throw new RuntimeException(e);
      }
      return target;
   }

   /**
    * 字段名称及值置入MAP
    * @param field      字段
    * @param object     对象
    * @param fieldMap   Map
    * @throws IllegalArgumentException
    * @throws IllegalAccessException
    */
   private static void populateFieldMap(Field field,
                                        Object object,
                                        Map<String, Object> fieldMap)
         throws IllegalArgumentException, IllegalAccessException{
      boolean isAccessible = field.isAccessible();
      field.setAccessible(true);
      fieldMap.put(field.getName(), field.get(object));
      field.setAccessible(isAccessible);
   }


   /**
    * 依据传入值获取值所属类型的集合
    * @param args
    * @return
    * @throws Exception
    */
   public static Class<?>[] getParameterTypes(Object[] args)
         throws Exception{
      if(args == null){
         return null;
      }
      Class<?>[] parameterTypes = new Class[args.length];
      for(int i = 0, j = args.length; i < j; i++){
         if(args[i] instanceof Integer){
            parameterTypes[i] = Integer.TYPE;
         }else if(args[i] instanceof Byte){
            parameterTypes[i] = Byte.TYPE;
         }else if(args[i] instanceof Short){
            parameterTypes[i] = Short.TYPE;
         }else if(args[i] instanceof Float){
            parameterTypes[i] = Float.TYPE;
         }else if(args[i] instanceof Double){
            parameterTypes[i] = Double.TYPE;
         }else if(args[i] instanceof Character){
            parameterTypes[i] = Character.TYPE;
         }else if(args[i] instanceof Long){
            parameterTypes[i] = Long.TYPE;
         }else if(args[i] instanceof Boolean){
            parameterTypes[i] = Boolean.TYPE;
         }else if(args[i] instanceof Map){
            parameterTypes[i] = Map.class;
         }else if(args[i] instanceof List){
            parameterTypes[i] = List.class;
         }else{
            parameterTypes[i] = args[i].getClass();
         }
      }
      return parameterTypes;
   }

   /**
    * 根据类型转换值
    * @param args
    * @param type
    * @return
    * @throws ParseException 
    */
   public static Object getValueByType(String args,
                                       Type type)
         throws ParseException{
      if(ObjectUtils.isEmpty(args)){
         return "";
      }
      Object obj = new Object();
      if(type.equals(Integer.class)){
         obj = Integer.valueOf(args);
      }else if(type.equals(Byte.class)){
         obj = Byte.valueOf(args);
      }else if(type.equals(Short.class)){
         obj = Short.valueOf(args);
      }else if(type.equals(Float.class)){
         obj = Float.valueOf(args);
      }else if(type.equals(Double.class)){
         obj = Double.valueOf(args);
      }else if(type.equals(Long.class)){
         obj = Long.valueOf(args);
      }else if(type.equals(Boolean.class)){
         obj = Boolean.valueOf(args);
      }else if(type.equals(String.class)){
         obj = String.valueOf(args).trim();
      }else if(type.equals(java.sql.Date.class)){
         obj = java.sql.Date.valueOf(args);
      }else if(type.equals(java.sql.Timestamp.class)){
         obj = java.sql.Timestamp.valueOf(args);
      }else if(type.equals(LocalDateTime.class)){
         obj = LocalDateTime.parse(args, DateTimeFormatter.ofPattern(DateUtil.TIME_FORMAT));
      }else if(type.equals(java.util.Date.class)){
         SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", java.util.Locale.US);
         obj = sdf.format(args);
      }
      return obj;
   }

}
