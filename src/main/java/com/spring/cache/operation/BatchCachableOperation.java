package com.spring.cache.operation;

import com.spring.cache.CacheExt;
import com.spring.cache.CacheExtManager;
import com.spring.cache.domain.CacheExtOperationContext;
import com.spring.cache.utils.ReflectExtUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/7/11.
 */
@Slf4j
public class BatchCachableOperation implements CacheOperation {
    @Setter
    private CacheExtManager cacheExtManager;

    public Object execute(CacheExtOperationContext context) throws Exception {
        if(context.getMethod().getReturnType() != Map.class){
            return process(context);
        }
        int paramIndex =  ReflectExtUtils.indexOfParamName(context.getMethod(),context.getOperationSource().getKeys());
        if(paramIndex==-1){
            return process(context);
        }
        Object[] args = context.getArgs();
        if(!(args[paramIndex] instanceof Collection)){
            return process(context);
        }
        Collection ids = (Collection)args[paramIndex];
        log.info("ids:{}",ids);
        CacheExt cache = cacheExtManager.getCache(context.getOperationSource().getCacheName());
        if(cache==null){
            return process(context);
        }
        Type returnType =context.getMethod().getGenericReturnType();
        List<Class> clazzList =  ReflectExtUtils.getGenericClass(returnType);
        //Map<K,V>里，V在clazzList里的1位置
        Class valueClazz = clazzList.get(1);
        Map<?, ?> dataInCache = cache.batchGet(ids,valueClazz);
        Set<?> keyInCache = null;
        if(dataInCache!=null){
            keyInCache =dataInCache.keySet();
        }


        Collection keyInQuery = (Collection)args[paramIndex];
        if(CollectionUtils.isNotEmpty(keyInCache)){
            keyInQuery.removeAll(keyInCache);
        }

        if(CollectionUtils.isEmpty(keyInQuery)){
            return dataInCache;
        }
        Map dataNotInCache = (Map)process(context);
        if(dataNotInCache==null){
            return dataInCache;
        }
        cache.batchPut(dataNotInCache,valueClazz);
        dataNotInCache.putAll(dataInCache);

        return dataNotInCache;
    }

    private Object process(CacheExtOperationContext context)  throws Exception{
        return context.getMethod().invoke(context.getInstance(),context.getArgs());
    }

}
