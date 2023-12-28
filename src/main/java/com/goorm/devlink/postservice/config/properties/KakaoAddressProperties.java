package com.goorm.devlink.postservice.config.properties;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("data.kakao.address")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
public class KakaoAddressProperties {

    private final String secretKey;
    private final String url;
}
