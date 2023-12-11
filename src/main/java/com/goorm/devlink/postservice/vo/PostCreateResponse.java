package com.goorm.devlink.postservice.vo;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostCreateResponse {

    private String postUuid;

    public static PostCreateResponse getInstance(String postUuid){
        return PostCreateResponse
                .builder()
                .postUuid(postUuid)
                .build();
    }
}
