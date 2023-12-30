package com.goorm.devlink.postservice.vo.response;

import com.goorm.devlink.postservice.vo.OnOffline;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostMatchingResponse {
    private String postUuid;
    private String userUuid;
    private OnOffline onOffline;
}
