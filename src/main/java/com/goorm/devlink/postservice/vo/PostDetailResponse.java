package com.goorm.devlink.postservice.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostDetailResponse {

    private String postUuid;
    private String postTitle;
    private String postImageUrl;
    private String postContent;
    private List<String> stacks;
    private OnOffline onOffline;
    private PostType postType;
    private String address;
    private String runningTime;
    private String userUuid;
    private PostStatus postStatus;
}
