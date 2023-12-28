package com.goorm.devlink.postservice.dto;

import com.goorm.devlink.postservice.entity.Address;
import com.goorm.devlink.postservice.vo.*;
import com.goorm.devlink.postservice.vo.request.PostCreateRequest;
import com.goorm.devlink.postservice.vo.request.PostEditRequest;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

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
    private Address address;
    private int unitTimeCount;
    private String userUuid;
    private PostStatus postStatus;


    public static PostBasicDto getInstanceForCreate(PostCreateRequest postCreateRequest,String postUuid, String postImageUrl, String userUuid, Address address){
        return PostBasicDto.builder()
                .postTitle(postCreateRequest.getPostTitle())
                .postImageUrl(postImageUrl)
                .postContent(postCreateRequest.getPostContent())
                .stacks(postCreateRequest.getStacks())
                .onOffline(postCreateRequest.getOnOffline())
                .postType(postCreateRequest.getPostType())
                .address(address)
                .unitTimeCount(postCreateRequest.getUnitTimeCount())
                .userUuid(userUuid)
                .postUuid(postUuid)
                .postStatus(PostStatus.WAITING)
                .build();
    }

    public static PostBasicDto getInstanceForEdit(PostEditRequest postEditRequest, Address address,String postImageUrl,String userUuid){
        return PostBasicDto.builder()
                .postTitle(postEditRequest.getPostTitle())
                .postImageUrl(postImageUrl)
                .postContent(postEditRequest.getPostContent())
                .stacks(postEditRequest.getStacks())
                .onOffline(postEditRequest.getOnOffline())
                .postType(postEditRequest.getPostType())
                .address(address)
                .unitTimeCount(postEditRequest.getUnitTimeCount())
                .postStatus(postEditRequest.getPostStatus())
                .postUuid(postEditRequest.getPostUuid())
                .userUuid(userUuid)
                .build();
    }
}
