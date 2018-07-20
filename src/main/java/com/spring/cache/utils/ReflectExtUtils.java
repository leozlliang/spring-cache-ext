package com.spring.cache.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/7/11.
 */
public class ReflectExtUtils {
    private static LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    public static String[] getParamNames(Method method){
        String[] params = parameterNameDiscoverer.getParameterNames(method);
        return params;
    }

    public static int indexOfParamName(Method method,String paramName){
        String[] paramNames = getParamNames(method);
        if(paramNames==null){
            return -1;
        }
        for(int i=0;i<paramNames.length;i++){
            String pName = paramNames[i];
            if(pName.equals(paramName)){
                return i;
            }
        }
        return -1;
    }

    public static List<Class> getGenericClass(Type type){
        List<Class> list = Lists.newArrayList();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            Class[] classes = new  Class[types.length];
            for(int i=0;i<classes.length;i++){
                Type claz = types[i];
                if( claz instanceof Class ){
                    list.add((Class)claz);
                }

            }
        }
        return list;
    }


    public static void main(String[] args) {
        HashMap<String,Long> map = new HashMap<String, Long>();
        map.put("1111",1111L);
        Cache<Object, Object> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(111L, TimeUnit.SECONDS)
                .build();
        System.out.print(getGenericClass(map.getClass().getGenericSuperclass()));
    }
}
