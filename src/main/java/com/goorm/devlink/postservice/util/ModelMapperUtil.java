package com.goorm.devlink.postservice.util;


import com.goorm.devlink.postservice.dto.PostBasicDto;
import com.goorm.devlink.postservice.entity.PostEntity;
import com.goorm.devlink.postservice.vo.response.PostDetailResponse;
import com.goorm.devlink.postservice.vo.response.PostMatchingResponse;
import com.goorm.devlink.postservice.vo.response.PostSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@RequiredArgsConstructor
public class ModelMapperUtil {

    private final ModelMapper mapper;

    public PostSimpleResponse convertToPostSimpleResponse(PostEntity postEntity){
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(postEntity,PostSimpleResponse.class);
    }

    public PostEntity convertToPostEntity(PostBasicDto postBasicDto) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        PostEntity postEntity = mapper.map(postBasicDto, PostEntity.class);
        return postEntity;
    }

    public PostDetailResponse convertToPostDetailResponse(PostEntity postEntity){
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(postEntity, PostDetailResponse.class);
    }

    public PostMatchingResponse converToPostMatchingResponse(PostEntity postEntity){
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(postEntity,PostMatchingResponse.class);
    }


}
