package com.goorm.devlink.postservice.util;


import com.goorm.devlink.postservice.entity.PostEntity;
import com.goorm.devlink.postservice.vo.PostSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
}
