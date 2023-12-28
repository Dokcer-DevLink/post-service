package com.goorm.devlink.postservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "profile-service")
public interface ProfileServiceClient {

    @GetMapping("/api/profile/stacks")
    public ResponseEntity<List<String>> viewUserStackList(@RequestParam("userUuid") String userUuid);

}