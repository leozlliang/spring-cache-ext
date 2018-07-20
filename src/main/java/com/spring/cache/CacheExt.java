package com.spring.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/11.
 */
public interface CacheExt {
    String getName();

    public <K,V> Map<K,V> batchGet(Collection<K> keys, Class<V> clazz);

    public <K,V>void batchPut(Map<K,V> cacheMap, Class<V> clazz);

    public void batchEvict(Collection<?> keys);
}
