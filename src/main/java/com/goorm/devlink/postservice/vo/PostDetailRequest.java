package com.goorm.devlink.postservice.vo;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
public class PostDetailRequest {

    private String postTitle;
    private String postImageUrl;
    private String PostContent;
    private List<String> stacks;
    private OnOffline onOffline;
    private String address;
    private String runningTime;

}
