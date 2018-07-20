package com.spring.cache;


import java.util.Collection;

/**
 * Created by Administrator on 2018/7/11.
 */
public interface CacheExtManager {
    CacheExt getCache(String var1);
    Collection<String> getCacheNames();
}
