package com.goorm.devlink.postservice.vo.response;


import com.goorm.devlink.postservice.entity.Address;
import com.goorm.devlink.postservice.entity.PostEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

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
                .address(getAddressName(post.getAddress()))
                .build();
    }

    private static String getAddressName(Address address){
        return ( address == null )? null : address.getAddressName();
    }


}
