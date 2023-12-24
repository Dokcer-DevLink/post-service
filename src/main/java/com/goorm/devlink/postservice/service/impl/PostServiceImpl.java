package com.goorm.devlink.postservice.service.impl;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.goorm.devlink.postservice.config.properties.vo.AwsConfigVo;
import com.goorm.devlink.postservice.config.properties.vo.PageConfigVo;
import com.goorm.devlink.postservice.dto.PostBasicDto;
import com.goorm.devlink.postservice.entity.PostEntity;
import com.goorm.devlink.postservice.repository.PostRepository;
import com.goorm.devlink.postservice.service.PostService;
import com.goorm.devlink.postservice.util.AwsUtil;
import com.goorm.devlink.postservice.util.MessageUtil;
import com.goorm.devlink.postservice.util.ModelMapperUtil;
import com.goorm.devlink.postservice.vo.PostDetailResponse;
import com.goorm.devlink.postservice.vo.PostSimpleResponse;
import com.goorm.devlink.postservice.vo.PostStatusRequest;
import com.goorm.devlink.postservice.vo.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PageConfigVo pageConfigVo;
    private final ModelMapperUtil modelMapperUtil;
    private final MessageUtil messageUtil;
    private final AwsUtil awsUtil;

    @Override
    public Page<PostSimpleResponse> getPostList(PostType postType, String keyword) {
        PageRequest pageRequest = PageRequest.of(pageConfigVo.getOffset(), pageConfigVo.getSize());
        Page<PostEntity> postPage = postRepository.findPostListByPostTypeAndKeyWord(postType, keyword, pageRequest);
        return postPage.map( post -> PostSimpleResponse.getInstance(post) );
    }

    @Override
    public Page<PostSimpleResponse> getMyPostList(PostType postType, String userUuid) {
        PageRequest pageRequest = PageRequest.of(pageConfigVo.getOffset(), pageConfigVo.getSize(),
                Sort.by(Sort.Direction.DESC,pageConfigVo.getOrderBy()));
        Page<PostEntity> postPage = postRepository.findByUserUuidAndPostType(userUuid,postType,pageRequest);
        return postPage.map( post -> PostSimpleResponse.getInstance(post) );
    }

    @Override
    public Page<PostSimpleResponse> getRecommendPostList(PostType postType, List<String> profileStacks) {
        PageRequest pageRequest = PageRequest.of(pageConfigVo.getOffset(), pageConfigVo.getSize());
        Page<PostEntity> postPage = postRepository.findPostListByPostTypeAndStacks(postType,profileStacks,pageRequest);
        return postPage.map( post -> PostSimpleResponse.getInstance(post) );
    }

    @Override
    public PostDetailResponse getDetailPost(String postUuid) {
        return modelMapperUtil.convertToPostDetailResponse(findPostEntity(postUuid));
    }

    @Override
    @Transactional
    public String createPost(PostBasicDto postBasicDto) {
        PostEntity postEntity = modelMapperUtil.convertToPostEntity(postBasicDto);
        postRepository.save(postEntity);
        return postEntity.getPostUuid();
    }

    @Override
    @Transactional
    public void editPost(PostBasicDto instanceForEdit) {
        PostEntity findPostEntity = findPostEntity(instanceForEdit.getPostUuid());
        PostEntity postEntity = modelMapperUtil.convertToPostEntity(instanceForEdit);
        postEntity.updateForMerge(findPostEntity.getId());
        postRepository.save(postEntity);
    }

    @Override
    @Transactional
    public void deletePost(String postUuid) {
        postRepository.delete(findPostEntity(postUuid));
    }

    @Override
    @Transactional
    public String updateStatus(PostStatusRequest postStatusRequest) {
        PostEntity findPost = findPostEntity(postStatusRequest.getPostUuid());
        findPost.updateStatus(postStatusRequest.getPostStatus());
        postRepository.save(findPost);
        return findPost.getPostUuid();
    }

    @Override
    public String savePostImageToS3Bucket(MultipartFile postImage) {
        return awsUtil.savePostImageToS3Bucket(postImage);
    }

    private PostEntity findPostEntity(String postUuid){
        return postRepository.findByPostUuid(postUuid).orElseThrow(() -> {
                    throw new NoSuchElementException(messageUtil.getPostUuidNoSuchMessage(postUuid));
                });
    }


}
