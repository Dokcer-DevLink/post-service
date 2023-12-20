package com.goorm.devlink.postservice.service;

import com.goorm.devlink.postservice.dto.PostBasicDto;
import com.goorm.devlink.postservice.vo.PostDetailResponse;
import com.goorm.devlink.postservice.vo.PostSimpleResponse;
import com.goorm.devlink.postservice.vo.PostStatusRequest;
import com.goorm.devlink.postservice.vo.PostType;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    String createPost(PostBasicDto postBasicDto);
    Page<PostSimpleResponse> getPostList(PostType postType, String keyword);
    void editPost(PostBasicDto instanceForEdit);
    void deletePost(String postUuid);
    PostDetailResponse getDetailPost(String postUuid);
    String updateStatus(PostStatusRequest postStatusRequest);
    Page<PostSimpleResponse> getMyPostList(PostType postType,String userUuid);
    Page<PostSimpleResponse> getRecommendPostList(PostType postType, List<String> profileStacks);

    String savePostImageToS3Bucket(MultipartFile postImage);
}
