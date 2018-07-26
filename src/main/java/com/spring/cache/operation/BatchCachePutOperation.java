package com.spring.cache.operation;

import com.google.common.collect.Maps;
import com.spring.cache.CacheExt;
import com.spring.cache.CacheExtManager;
import com.spring.cache.domain.CacheExtOperationContext;
import com.spring.cache.domain.CacheExtOperationSource;
import com.spring.cache.utils.ReflectExtUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/11.
 */
@Slf4j
public class BatchCachePutOperation extends BaseCacheOperation {
    @Setter
    private CacheExtManager cacheExtManager;

    private ExpressionParser parser = new SpelExpressionParser();

    public Object execute(CacheExtOperationContext context) throws Exception {
        if(validate(context)){
            return process(context);
        }

        Map<Object,Object> data = (Map)context.getArgs()[0];
        CacheExt cache = cacheExtManager.getCache(context.getOperationSource().getCacheName());

        //逻辑处理
        Map<String,Object> caches = Maps.newHashMap();
        for(Map.Entry<?,Object> entry:data.entrySet()){
            String newKey = context.getOperationSource().getKeyPrefix() + String.valueOf(entry.getKey());
            caches.put(newKey,entry.getValue());
        }

        Type returnType =context.getMethod().getGenericReturnType();
        List<Class> clazzList =  ReflectExtUtils.getGenericClass(returnType);
        //Map<K,V>里，V在clazzList里的1位置
        Class valueClazz = clazzList.get(1);
        cache.batchPut(caches,valueClazz);
        return process(context);
    }

    private boolean validate(CacheExtOperationContext context){
        Object[] args = context.getArgs();
        if(args==null || args.length>1 || !(args[0] instanceof Map)){
            return false;
        }
        Map<Object,Object> data = (Map)args[0];
        if(MapUtils.isEmpty(data)){
            return false;
        }
        CacheExt cache = cacheExtManager.getCache(context.getOperationSource().getCacheName());
        if(cache==null){
            return false;
        }
        return true;
    }

    private Object process(CacheExtOperationContext context)  throws Exception{
        return context.getMethod().invoke(context.getInstance(),context.getArgs());
    }

    private String getKeyFromSpel(String spel,Object param){
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setRootObject(param);
        String newSpel = spel.replace("#","#root.");
        Object key = parser.parseExpression(newSpel).getValue(context);
        return key.toString();
    }

    public static void main(String[] args) {
        CacheExtOperationSource source = CacheExtOperationSource.builder().keys("resid").cacheName("guava").build();
        String spel = "#keys +'_'+ #cacheName";

        BatchCachePutOperation operation = new BatchCachePutOperation();

        System.out.println(operation.getKeyFromSpel(spel,source));
    }

}
