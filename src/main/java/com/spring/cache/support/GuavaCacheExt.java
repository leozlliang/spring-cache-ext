package com.spring.cache.support;

import com.google.common.base.Function;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.spring.cache.CacheExt;
import lombok.Setter;
import org.springframework.beans.factory.BeanNameAware;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/7/11.
 */


public class GuavaCacheExt implements CacheExt,BeanNameAware {
    private String name;
    private long expire;
    private  Cache<Object, Object> cache;


    public String getName() {
        return name;
    }



    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }



    @PostConstruct
    public void init(){
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(expire,TimeUnit.SECONDS)
                .build();
    }


    public <K, V> Map<K, V> batchGet(Collection<K> keys, Class<V> clazz) {
        return ( Map<K, V>)cache.getAllPresent(keys);
    }

    public <K,V>void batchPut(Map<K,V> cacheMap, Class<V> clazz){
        cache.putAll(cacheMap);
    }

    public void batchEvict(Collection<?> keys) {

    }


    public void setBeanName(String beanName) {
        name =beanName;
    }
}
