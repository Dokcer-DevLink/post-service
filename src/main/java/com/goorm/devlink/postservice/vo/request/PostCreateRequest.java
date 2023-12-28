package com.goorm.devlink.postservice.vo.request;


import com.goorm.devlink.postservice.vo.OnOffline;
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
public class PostCreateRequest {

    @NotBlank(message = "{request.required}")
    private String postTitle;
    private String PostContent;
    private List<String> stacks;
    @NotNull(message = "{request.required}")
    private OnOffline onOffline;
    @NotNull(message = "{request.required}")
    private PostType postType;
    private String address;
    @NotBlank(message = "{request.required}")
    private String runningTime;
    private String postImage;




}
