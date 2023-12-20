package com.goorm.devlink.postservice.dto;

import com.goorm.devlink.postservice.vo.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class PostBasicDto {
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


    public static PostBasicDto getInstanceForCreate(PostCreateRequest postCreateRequest, String postImageUrl, String userUuid){
        return PostBasicDto.builder()
                .postTitle(postCreateRequest.getPostTitle())
                .postImageUrl(postImageUrl)
                .postContent(postCreateRequest.getPostContent())
                .stacks(postCreateRequest.getStacks())
                .onOffline(postCreateRequest.getOnOffline())
                .postType(postCreateRequest.getPostType())
                .address(postCreateRequest.getAddress())
                .runningTime(postCreateRequest.getRunningTime())
                .userUuid(userUuid)
                .postUuid(UUID.randomUUID().toString())
                .postStatus(PostStatus.WAITING)
                .build();
    }

    public static PostBasicDto getInstanceForEdit(PostEditRequest postEditRequest){
        return PostBasicDto.builder()
                .postTitle(postEditRequest.getPostTitle())
                .postImageUrl(postEditRequest.getPostImageUrl())
                .postContent(postEditRequest.getPostContent())
                .stacks(postEditRequest.getStacks())
                .onOffline(postEditRequest.getOnOffline())
                .postType(postEditRequest.getPostType())
                .address(postEditRequest.getAddress())
                .runningTime(postEditRequest.getRunningTime())
                .postStatus(postEditRequest.getPostStatus())
                .postUuid(postEditRequest.getPostUuid())
                .build();
    }
}
