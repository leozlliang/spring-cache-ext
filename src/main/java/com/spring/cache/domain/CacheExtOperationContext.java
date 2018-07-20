package com.spring.cache.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/7/11.
 */
@Getter
@Setter
@Builder
@ToString
public class CacheExtOperationContext {
    private Object instance;
    private Method method;
    private Object[] args;
    private CacheExtOperationSource operationSource;
}
