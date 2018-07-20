package com.spring.cache.support;

import com.spring.cache.CacheExt;

import java.util.Collection;

/**
 * Created by Administrator on 2018/7/11.
 */
public class SimpleCacheExtManager extends AbstractCacheExtManager {
    private Collection<? extends CacheExt> caches;

    public SimpleCacheExtManager() {
    }

    public void setCaches(Collection<? extends CacheExt> caches) {
        this.caches = caches;
    }

    protected Collection<? extends CacheExt> loadCaches() {
        return this.caches;
    }
}
