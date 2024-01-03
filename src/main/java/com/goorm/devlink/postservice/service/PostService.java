package com.goorm.devlink.postservice.service;

import com.goorm.devlink.postservice.dto.PostBasicDto;
import com.goorm.devlink.postservice.entity.Address;
import com.goorm.devlink.postservice.vo.S3ImageVo;
import com.goorm.devlink.postservice.vo.request.PostMatchingRequest;
import com.goorm.devlink.postservice.vo.response.MentoringPostResponse;
import com.goorm.devlink.postservice.vo.response.PostDetailResponse;
import com.goorm.devlink.postservice.vo.response.PostMatchingResponse;
import com.goorm.devlink.postservice.vo.response.PostSimpleResponse;
import com.goorm.devlink.postservice.vo.request.PostStatusRequest;
import com.goorm.devlink.postservice.vo.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PostService {
    String createPost(PostBasicDto postBasicDto);
    Slice<PostSimpleResponse> getPostList(PostType postType, String keyword, Pageable pageable);
    void editPost(PostBasicDto instanceForEdit);
    void deletePost(String postUuid);
    PostDetailResponse getDetailPost(String postUuid);
    String updateStatus(PostStatusRequest postStatusRequest);
    Page<PostSimpleResponse> getMyPostList(PostType postType,String userUuid);
    Page<PostSimpleResponse> getRecommendPostList(PostType postType, List<String> profileStacks);

    String savePostImageToS3Bucket(S3ImageVo s3ImageVo);

    Address createAddress(String address);

    List<PostMatchingResponse> getPostMatchingData(PostMatchingRequest postMatchingRequest);

    List<MentoringPostResponse> getPostListForMentoring(List<String> postUuids);
}
