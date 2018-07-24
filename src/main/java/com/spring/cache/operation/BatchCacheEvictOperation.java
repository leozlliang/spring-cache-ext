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
public class BatchCacheEvictOperation extends BaseCacheOperation {
    @Setter
    private CacheExtManager cacheExtManager;

    public Object execute(CacheExtOperationContext context) throws Exception {

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
       cache.batchEvict(ids);

        return process(context);
    }

    private Object process(CacheExtOperationContext context)  throws Exception{
        return context.getMethod().invoke(context.getInstance(),context.getArgs());
    }

}
