package com.maveric.commentservice.feign;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="like-service",fallbackFactory = HystrixFallBackFactory.class)
public interface LikeFeign {
@LoadBalanced
    @GetMapping("/api/v1/postOrCommentId/{postOrCommentId}/likes/count")
    public ResponseEntity<Integer> getLikesCount(@PathVariable("postOrCommentId") String postOrCommentId);


}
