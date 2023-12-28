package com.goorm.devlink.postservice.vo.request;


import com.goorm.devlink.postservice.vo.PostStatus;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class PostStatusRequest {

    @NotBlank(message = "{request.required}")
    private String postUuid;
    @NotNull(message = "{request.required}")
    private PostStatus postStatus;
}
