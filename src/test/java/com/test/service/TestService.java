package com.test.service;


import com.google.common.collect.Maps;
import com.spring.cache.annotation.BatchCachable;
import com.test.bean.TestBean;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/11.
 */
@Slf4j
public class TestService {

    @BatchCachable(cacheName = "guavaCache",keyPrefix="bean:",keys = "ids")
    public Map<Integer, TestBean> queryBeanList(List<Integer> ids){
        log.info("===get not in cache");
        Map<Integer, TestBean> maps = Maps.newHashMap();
        for(Integer id:ids){
            TestBean bean = new TestBean();
            bean.setId(id);
            bean.setName("name:"+id);
            maps.put(id,bean);
        }
        return maps;
    }
}
