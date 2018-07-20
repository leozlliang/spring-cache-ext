package com.spring.cache.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2018/7/10.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@BasicAnnotation
public @interface BatchCachable {
    String cacheName() default "";

    String keys() default "";

    String keyPrefix() default "";

    long expire() default 0L;

}
