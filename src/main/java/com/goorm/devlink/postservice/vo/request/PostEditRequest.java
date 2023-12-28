package com.goorm.devlink.postservice.vo.request;


import com.goorm.devlink.postservice.vo.OnOffline;
import com.goorm.devlink.postservice.vo.PostStatus;
import com.goorm.devlink.postservice.vo.PostType;
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
    private String postUuid;
    @NotBlank(message = "{request.required}")
    private String postTitle;

    private String postContent;

    private List<String> stacks;

    @NotNull(message = "{request.required}")
    private OnOffline onOffline;

    @NotNull(message = "{request.required}")
    private PostType postType;

    @NotNull(message = "{request.required}")
    private PostStatus postStatus;

    private String address;

    private int unitTimeCount;

    private String postImage;




}
