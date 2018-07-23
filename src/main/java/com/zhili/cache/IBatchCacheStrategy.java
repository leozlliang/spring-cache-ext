package com.zhili.cache;

import java.util.List;
import java.util.Set;

/**
 * Created by zhili liang on 2018/6/28.
 */
public interface IBatchCacheStrategy<K,V> {
    public  List<V> getNotFromCache(List<K> keys);
    public  List<V> getFromCache(List<K> keys);
    public  K getKey(V value);
    public  void batchPut(List<V> dataList);


}
