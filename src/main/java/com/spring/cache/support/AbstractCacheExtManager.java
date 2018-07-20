package com.spring.cache.support;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.spring.cache.CacheExt;
import com.spring.cache.CacheExtManager;
import org.springframework.beans.factory.InitializingBean;


public abstract class AbstractCacheExtManager implements CacheExtManager, InitializingBean {
    private final ConcurrentMap<String, CacheExt> cacheMap = new ConcurrentHashMap(16);
    private volatile Set<String> cacheNames = Collections.emptySet();

    public AbstractCacheExtManager() {
    }

    public void afterPropertiesSet() {
        this.initializeCaches();
    }

    public void initializeCaches() {
        Collection caches = this.loadCaches();
        ConcurrentMap var2 = this.cacheMap;
        synchronized(this.cacheMap) {
            this.cacheNames = Collections.emptySet();
            this.cacheMap.clear();
            LinkedHashSet cacheNames = new LinkedHashSet(caches.size());
            Iterator var4 = caches.iterator();

            while(var4.hasNext()) {
                CacheExt cache = (CacheExt)var4.next();
                String name = cache.getName();
                this.cacheMap.put(name, this.decorateCache(cache));
                cacheNames.add(name);
            }

            this.cacheNames = Collections.unmodifiableSet(cacheNames);
        }
    }

    protected abstract Collection<? extends CacheExt> loadCaches();

    public CacheExt getCache(String name) {
        CacheExt cache = (CacheExt)this.cacheMap.get(name);
        if(cache != null) {
            return cache;
        } else {
            ConcurrentMap var3 = this.cacheMap;
            synchronized(this.cacheMap) {
                cache = (CacheExt)this.cacheMap.get(name);
                if(cache == null) {
                    cache = this.getMissingCache(name);
                    if(cache != null) {
                        cache = this.decorateCache(cache);
                        this.cacheMap.put(name, cache);
                        this.updateCacheNames(name);
                    }
                }

                return cache;
            }
        }
    }

    public Collection<String> getCacheNames() {
        return this.cacheNames;
    }

    protected final CacheExt lookupCache(String name) {
        return (CacheExt)this.cacheMap.get(name);
    }

    protected final void addCache(CacheExt cache) {
        String name = cache.getName();
        ConcurrentMap var3 = this.cacheMap;
        synchronized(this.cacheMap) {
            if(this.cacheMap.put(name, this.decorateCache(cache)) == null) {
                this.updateCacheNames(name);
            }

        }
    }

    private void updateCacheNames(String name) {
        LinkedHashSet cacheNames = new LinkedHashSet(this.cacheNames.size() + 1);
        cacheNames.addAll(this.cacheNames);
        cacheNames.add(name);
        this.cacheNames = Collections.unmodifiableSet(cacheNames);
    }

    protected CacheExt decorateCache(CacheExt cache) {
        return cache;
    }

    protected CacheExt getMissingCache(String name) {
        return null;
    }
}
