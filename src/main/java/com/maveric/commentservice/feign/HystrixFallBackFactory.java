package com.maveric.commentservice.feign;

import org.springframework.cloud.openfeign.FallbackFactory;

public class HystrixFallBackFactory implements FallbackFactory<LikeFeign> {
    @Override
    public LikeFeign create(Throwable cause) {

        return null;

    }
}
