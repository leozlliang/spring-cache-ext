package com.test;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.test.bean.TestBean;
import com.test.service.TestService;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/11.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:app-ctx-common.xml")
public class TestAAA {
    @Autowired
    private TestService testService;

    @Test
    public void test(){
        List<Integer> list = Lists.newArrayList(1,2,3);
        Map<Integer, TestBean> result = testService.queryBeanList(list);
        log.info("=====result1:{}",result);
        result = testService.queryBeanList(Lists.newArrayList(1,2));
        log.info("=====result2:{}", result);
    }
}
