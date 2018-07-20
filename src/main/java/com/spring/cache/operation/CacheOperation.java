package com.spring.cache.operation;

import com.spring.cache.domain.CacheExtOperationContext;

/**
 * Created by Administrator on 2018/7/11.
 */
public interface CacheOperation {
    public Object execute(CacheExtOperationContext context) throws Exception;
}
