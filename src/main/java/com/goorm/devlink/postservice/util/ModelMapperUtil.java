package com.goorm.devlink.postservice.util;


import com.goorm.devlink.postservice.entity.PostEntity;
import com.goorm.devlink.postservice.entity.StackEntity;
import com.goorm.devlink.postservice.vo.PostDetailRequest;
import com.goorm.devlink.postservice.vo.PostSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ModelMapperUtil {

    private final ModelMapper mapper;


    public List<PostSimpleResponse> convertToPostSimpleResponseList(List<PostEntity> findPostList) {
        List<PostSimpleResponse> postSimpleResponseList = new ArrayList<>();
        for (PostEntity postEntity : findPostList) {
            postSimpleResponseList.add(PostSimpleResponse.getInstance(postEntity));
        }
        return postSimpleResponseList;
    }

    public PostEntity convertToPostEntity(PostDetailRequest postDetailRequest) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        PostEntity postEntity = mapper.map(postDetailRequest, PostEntity.class);
        postEntity.generatePostUuid();;
        return postEntity;
    }

    public List<StackEntity> convertToStackEntityList(List<String> stackNames, PostEntity postEntity){
        List<StackEntity> stackEntityList = new ArrayList<>();
        for (String stackName : stackNames) {
            stackEntityList.add(StackEntity.getInstance(stackName,postEntity));
        }
        return stackEntityList;
    }
}
