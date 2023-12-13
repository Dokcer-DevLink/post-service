package com.goorm.devlink.postservice.vo;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
public class PostEditRequest {

    @NotBlank(message = "{request.required}")
    private String postTitle;
    @NotBlank(message = "{request.required}")
    private String postImageUrl;
    private String PostContent;
    private List<String> stacks;
    @NotNull(message = "{request.required}")
    private OnOffline onOffline;
    @NotNull(message = "{request.required}")
    private PostType postType;
    @NotNull(message = "{request.required}")
    private PostStatus postStatus;
    private String address;
    @NotBlank(message = "{request.required}")
    private String runningTime;
    @NotBlank(message = "{request.required}")
    private String postUuid;


}
