package com.goorm.devlink.postservice.vo;


import lombok.Getter;

@Getter
public class PostStatusRequest {

    private String postUuid;
    private PostStatus postStatus;
}
