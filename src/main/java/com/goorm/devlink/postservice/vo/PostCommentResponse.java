package com.goorm.devlink.postservice.vo;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostCommentResponse {

    private String postUuid;
    private String message;

    public static PostCommentResponse getInstanceForCreate(String postUuid){
        return PostCommentResponse
                .builder()
                .postUuid(postUuid)
                .message("Post is created.")
                .build();
    }

    public static PostCommentResponse getInstanceForEdit(String postUuid){
        return PostCommentResponse
                .builder()
                .postUuid(postUuid)
                .message("Post is edited.")
                .build();
    }
}
