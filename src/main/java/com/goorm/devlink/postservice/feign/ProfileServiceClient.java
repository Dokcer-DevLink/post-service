package com.goorm.devlink.postservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "profile-service")
public interface ProfileServiceClient {

    @GetMapping("/api/profile/stacks")
    public List<String> viewUserStackList(@RequestHeader("userUuid") String userUuid);

}