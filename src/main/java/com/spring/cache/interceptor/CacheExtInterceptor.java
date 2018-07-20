package com.spring.cache.interceptor;

import com.spring.cache.CacheExtManager;
import com.spring.cache.annotation.BatchCachable;
import com.spring.cache.domain.CacheExtOperationContext;
import com.spring.cache.domain.CacheExtOperationSource;
import com.spring.cache.operation.BatchCachableOperation;
import com.spring.cache.operation.CacheOperation;
import lombok.Setter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/7/11.
 */
@Component
public class CacheExtInterceptor implements MethodInterceptor,InitializingBean {
    @Autowired
    CacheExtManager cacheExtManager;

    private BatchCachableOperation batchCachableOperation =  new BatchCachableOperation();

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {



        //Object result = methodInvocation.proceed();
        Method method = methodInvocation.getMethod();
        CacheExtOperationContext context = CacheExtOperationContext.builder().method(method).
                                                                                args(methodInvocation.getArguments())
                                                                                .instance(methodInvocation.getThis()).build();
        BatchCachable cachableVal = AnnotationUtils.findAnnotation(method,BatchCachable.class);
        if(cachableVal!=null){
            CacheExtOperationSource source = CacheExtOperationSource.builder()
                                                                    .cacheName(cachableVal.cacheName()).keys(cachableVal.keys())
                                                                    .keyPrefix(cachableVal.keyPrefix()).expire(cachableVal.expire()).build();
            context.setOperationSource(source);
            return batchCachableOperation.execute(context);
        }
        return null;
    }

    public void afterPropertiesSet() throws Exception {
        batchCachableOperation.setCacheExtManager(cacheExtManager);
    }
}
