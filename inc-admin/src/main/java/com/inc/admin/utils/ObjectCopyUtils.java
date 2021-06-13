package com.inc.admin.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ObjectCopyUtils
{
    
    /**
     * target 目标对象 
     * source 被复制属性的对象 返回复制属性后的目标对象
     */
    public static <T> T copyProperties(T target, Object source)
    {
        try
        {
            PropertyUtils.copyProperties(target, source);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            return null;
        }
        return target;
    }

    /**
     * 不拷贝空属性
     * target 目标对象
     * source 被复制属性的对象 返回复制属性后的目标对象
     */
    public static <T> T copyNotNullProperties(T target, Object source)
    {
        BeanUtils.copyProperties(source, target, getNullField(source));
        return  target;
    }

    /**
     * 获取属性中为空的字段
     *
     * @param target
     * @return
     */
    public static String[] getNullField(Object target, boolean... isNotBlank) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(target);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        Set<String> notNullFieldSet = new HashSet<>();
        if (propertyDescriptors.length > 0) {
            for (PropertyDescriptor p : propertyDescriptors) {
                String name = p.getName();
                Object value = beanWrapper.getPropertyValue(name);
                boolean judgeBlank =  isNotBlank.length>0 && isNotBlank[0] && value instanceof String && "".equals(String.valueOf(value));
                if (Objects.isNull(value) || judgeBlank) {
                    notNullFieldSet.add(name);
                }
            }
        }
        String[] notNullField = new String[notNullFieldSet.size()];
        return notNullFieldSet.toArray(notNullField);
    }
    /**
     * 接口查询数据库数据返回,bigdecimal格式转换为string类型，查询数据后不需要重新赋值
     * 
     * @author mengfei
     * @param target
     * @param source
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static <T> T fillObject(T target, Object source)
    {
        try
        {
            Field[] fs = source.getClass().getDeclaredFields();
            for (int i = 0; i < fs.length; i++)
            {
                Class<?> valType = fs[i].getType();
                
                Field targetFied;
                try
                {
                    targetFied = target.getClass().getDeclaredField(fs[i].getName());
                    targetFied.setAccessible(true);
                    fs[i].setAccessible(true);
                    if (valType == BigDecimal.class && fs[i].get(source) != null)
                    {
                        targetFied.set(target,
                            ((Object)((BigDecimal)fs[i].get(source)).setScale(2, RoundingMode.HALF_UP) + ""));
                    }
                    else
                    {
                        targetFied.set(target, fs[i].get(source));
                    }
                }
                catch (NoSuchFieldException e)
                {
                    
                }
                
            }
        }
        catch (IllegalAccessException e)
        {
            return null;
        }
        return target;
    }
    
    /**
     * target 目标list的泛型 source 被复制属性的对象list 返回复制属性后的目标list
     */
    public static <T> List<T> copyListProperty(Class<T> target, List<?> sourceList)
    {
        List<T> targetList = new ArrayList<T>();
        try
        {
            for (Object source : sourceList)
            {
                targetList.add(copyProperties((T)target.newInstance(), source));
            }
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            return null;
        }
        return targetList;
    }
    
    /**
     * resultList：Query.getResultList()方法返回的结果<br>
     * target：目标类<br>
     * target的属性顺序必须跟NativeQuery执行的sql返回字段顺序一致<br>
     * @param <T>
     * @param resultList
     * @param target
     * @return
     * @throws Exception
     */
    public static <T> List<T> copyListProperty(List<Object[]> resultList, Class<T> target) throws Exception {
        List<T> results = new ArrayList<T>();
        for (Object[] result : resultList) {
            T instance = (T) target.newInstance();
            Field[] fields = instance.getClass().getDeclaredFields();
            for (int i = 0; i < result.length; i++) {
                fields[i].setAccessible(true);
                fields[i].set(instance, result[i]);
            }
            results.add(instance);
        }
        return results;
    }
    
}
