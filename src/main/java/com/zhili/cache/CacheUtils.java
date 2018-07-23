package com.zhili.cache;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by zhili liang on 2018/6/28.
 */
public class CacheUtils {

    public static <K,V>List<V> mulitGetByKeys(Collection<K> keys, IBatchCacheStrategy<K,V> strategy){
        List<V> result = Lists.newArrayList();
        Set keySet = Sets.newHashSet(keys);
        List keyList = Lists.newArrayList(keys);
        //先从缓存获取
        List<V> dataInCache = strategy.getFromCache(keyList);
        Set<K> keySetInCache = Sets.newHashSet();
        if(!CollectionUtils.isEmpty(dataInCache)){
            for(V data :dataInCache){
                K key = strategy.getKey(data);
                keySetInCache.add(key);
            }
        }
        result.addAll(dataInCache);
        //缓存获取不了的，由用户自定义获取
        Set<K> keySetNotInCache = Sets.symmetricDifference(keySet,keySetInCache);
        if(CollectionUtils.isEmpty(keySetNotInCache)){
            return dataInCache;
        }
        List<K> keyListNotInCache = Lists.newArrayList(keySetNotInCache);
        List<V> dataNotInCache = strategy.getNotFromCache(keyListNotInCache);
        //把用户自定义的放进缓存
        strategy.batchPut(dataNotInCache);
        result.addAll(dataNotInCache);
        return result;
    }
}
