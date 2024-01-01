package com.goorm.devlink.postservice.vo.response;

import com.goorm.devlink.postservice.entity.PostEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MentoringPostResponse {
    private String postUuid;
    private String postImageUrl;
    private String postTitle;
    private List<String> stacks;
    private String address;


    public static MentoringPostResponse convert(PostEntity postEntity){
        return MentoringPostResponse.builder()
                .postUuid(postEntity.getPostUuid())
                .postTitle(postEntity.getPostTitle())
                .postImageUrl(postEntity.getPostImageUrl())
                .stacks(postEntity.getStacks())
                .address(postEntity.getAddress().getAddressName())
                .build();
    }
}
