package com.goorm.devlink.postservice.vo.response;

import com.goorm.devlink.postservice.entity.Address;
import com.goorm.devlink.postservice.vo.OnOffline;
import com.goorm.devlink.postservice.vo.PostStatus;
import com.goorm.devlink.postservice.vo.PostType;
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
    private Address address;
    private int unitTimeCount;
    private String userUuid;
    private PostStatus postStatus;
}
