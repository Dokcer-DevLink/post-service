package com.goorm.devlink.postservice.vo;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Builder
public class PostCreateRequest {

    @NotBlank(message = "{required.request}")
    private String postTitle;
    @NotBlank(message = "{required.request}")
    private String postImageUrl;
    private String PostContent;
    private List<String> stacks;
    @NotBlank(message = "{required.request}")
    private OnOffline onOffline;
    @NotBlank(message = "{required.request}")
    private PostType postType;
    @NotBlank(message = "{required.request}")
    private PostStatus postStatus;
    private String address;
    @NotBlank(message = "{required.request}")
    private String runningTime;


}
