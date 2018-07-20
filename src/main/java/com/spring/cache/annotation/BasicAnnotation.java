package com.spring.cache.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2018/7/12.
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BasicAnnotation {
    String baseName() default "";
}
