package com.goorm.devlink.postservice.vo;


import com.goorm.devlink.postservice.entity.PostEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PostSimpleResponse {

    private String postUuid;
    private String postTitle;
    private String postImageUrl;
    private List<String> stacks;
    private String address;


    public static PostSimpleResponse getInstance(PostEntity post){
        return PostSimpleResponse.builder()
                .postUuid(post.getPostUuid())
                .postTitle(post.getPostTitle())
                .postImageUrl(post.getPostImageUrl())
                .stacks(post.getStacks())
                .address(post.getAddress())
                .build();
    }


}
