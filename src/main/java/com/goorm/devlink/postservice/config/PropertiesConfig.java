package com.goorm.devlink.postservice.config;


import com.goorm.devlink.postservice.config.properties.PageConfigProperties;
import com.goorm.devlink.postservice.config.properties.vo.PageConfigVo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({PageConfigProperties.class})
@RequiredArgsConstructor
public class PropertiesConfig {

    private final PageConfigProperties pageConfigProperties;

    @Bean
    public PageConfigVo pageConfigVo(){
        return new PageConfigVo(pageConfigProperties.getOffset(),
                pageConfigProperties.getSize(),
                pageConfigProperties.getOrderBy());
    }



}
