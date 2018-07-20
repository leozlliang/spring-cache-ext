package com.spring.cache.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2018/7/10.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BatchCacheEvict {
}
