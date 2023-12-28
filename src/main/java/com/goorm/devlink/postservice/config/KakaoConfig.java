package com.goorm.devlink.postservice.config;

import com.goorm.devlink.postservice.config.properties.KakaoAddressProperties;
import com.goorm.devlink.postservice.config.properties.vo.KakaoAddressVo;
import com.goorm.devlink.postservice.util.KakaoAddressUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(KakaoAddressProperties.class)
@RequiredArgsConstructor
public class KakaoConfig {

    private final KakaoAddressProperties kakaoAddressProperties;

    @Bean
    public KakaoAddressVo kakaoAddressVo(){
        return new KakaoAddressVo(kakaoAddressProperties.getSecretKey(), kakaoAddressProperties.getUrl());
    }

    @Bean
    public KakaoAddressUtil kakaoAddressUtil(KakaoAddressVo kakaoAddressVo){
        return new KakaoAddressUtil(kakaoAddressVo, new RestTemplate());
    }
}
