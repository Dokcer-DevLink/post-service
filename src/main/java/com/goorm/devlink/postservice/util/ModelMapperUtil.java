package com.goorm.devlink.postservice.util;


import com.goorm.devlink.postservice.dto.PostBasicDto;
import com.goorm.devlink.postservice.entity.PostEntity;
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

    public PostEntity convertToPostEntity(PostBasicDto postBasicDto) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        PostEntity postEntity = mapper.map(postBasicDto, PostEntity.class);
        return postEntity;
    }

//    public List<StackEntity> convertToStackEntityList(List<String> stackNames, PostEntity postEntity){
//        List<StackEntity> stackEntityList = new ArrayList<>();
//        for (String stackName : stackNames) {
//            stackEntityList.add(StackEntity.getInstance(stackName,postEntity));
//        }
//        return stackEntityList;
//    }
}
