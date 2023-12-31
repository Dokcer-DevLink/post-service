package com.goorm.devlink.postservice.vo.request;

import com.goorm.devlink.postservice.entity.Address;
import com.goorm.devlink.postservice.vo.OnOffline;
import com.goorm.devlink.postservice.vo.PostType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class PostMatchingRequest {

    private List<String> stacks;
    private Address address;
    private PostType postType;
    private OnOffline onOffline;

}
