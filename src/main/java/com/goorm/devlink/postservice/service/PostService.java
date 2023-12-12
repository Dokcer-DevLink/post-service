package com.goorm.devlink.postservice.service;

import com.goorm.devlink.postservice.dto.PostBasicDto;
import com.goorm.devlink.postservice.vo.PostDetailResponse;
import com.goorm.devlink.postservice.vo.PostSimpleResponse;
import com.goorm.devlink.postservice.vo.PostType;

import java.util.List;

public interface PostService {
    String createPost(PostBasicDto postBasicDto);
    List<PostSimpleResponse> getPostList(PostType postType, String keyword);
    void editPost(PostBasicDto instanceForEdit);
    void deletePost(String postUuid);
    PostDetailResponse getDetailPost(String postUuid);
}
