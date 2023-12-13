package com.goorm.devlink.postservice.dto;

import com.goorm.devlink.postservice.vo.OnOffline;
import com.goorm.devlink.postservice.vo.PostDetailRequest;
import com.goorm.devlink.postservice.vo.PostStatus;
import com.goorm.devlink.postservice.vo.PostType;
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


    public static PostBasicDto getInstanceForCreate(PostDetailRequest postDetailRequest, String userUuid){
        return PostBasicDto.builder()
                .postTitle(postDetailRequest.getPostTitle())
                .postImageUrl(postDetailRequest.getPostImageUrl())
                .postContent(postDetailRequest.getPostContent())
                .stacks(postDetailRequest.getStacks())
                .onOffline(postDetailRequest.getOnOffline())
                .postType(postDetailRequest.getPostType())
                .address(postDetailRequest.getAddress())
                .runningTime(postDetailRequest.getRunningTime())
                .userUuid(userUuid)
                .postUuid(UUID.randomUUID().toString())
                .postStatus(PostStatus.WAITING)
                .build();
    }

    public static PostBasicDto getInstanceForEdit(PostDetailRequest postDetailRequest){
        return PostBasicDto.builder()
                .postTitle(postDetailRequest.getPostTitle())
                .postImageUrl(postDetailRequest.getPostImageUrl())
                .postContent(postDetailRequest.getPostContent())
                .stacks(postDetailRequest.getStacks())
                .onOffline(postDetailRequest.getOnOffline())
                .postType(postDetailRequest.getPostType())
                .address(postDetailRequest.getAddress())
                .runningTime(postDetailRequest.getRunningTime())
                .postStatus(postDetailRequest.getPostStatus())
                .postUuid(postDetailRequest.getPostUuid())
                .build();
    }
}
