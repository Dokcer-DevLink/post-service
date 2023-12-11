package com.goorm.devlink.postservice.service;

import com.goorm.devlink.postservice.vo.PostDetailRequest;
import com.goorm.devlink.postservice.vo.PostSimpleResponse;
import com.goorm.devlink.postservice.vo.PostType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {
    String createPost(PostDetailRequest postDetailRequest);


    List<PostSimpleResponse> getPostList(PostType postType, String keyword);
}
