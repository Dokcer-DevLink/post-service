package com.goorm.devlink.postservice.vo;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostSimpleResponse {

    private String postUuid;
    private String postTitle;
    private String postImageUrl;
    private String[] stacks;
    private String address;
}
