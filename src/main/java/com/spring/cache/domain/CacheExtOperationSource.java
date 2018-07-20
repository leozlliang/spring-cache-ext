package com.spring.cache.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Administrator on 2018/7/11.
 */
@Getter
@Setter
@Builder
@ToString
public class CacheExtOperationSource {
    private String cacheName;
    private String keys;
    private String keyPrefix;
    private long expire;


}
