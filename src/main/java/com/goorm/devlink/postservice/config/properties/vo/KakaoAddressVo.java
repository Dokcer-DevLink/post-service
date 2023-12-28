package com.goorm.devlink.postservice.config.properties.vo;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class KakaoAddressVo {

    private final String secretKey;
    private final String url;
}
