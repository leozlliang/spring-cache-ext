package com.spring.cache.operation;

import com.spring.cache.CacheExtManager;
import lombok.Setter;

/**
 * Created by Administrator on 2018/7/24.
 */
public abstract class BaseCacheOperation implements CacheOperation {
    @Setter
    private CacheExtManager cacheExtManager;
}
