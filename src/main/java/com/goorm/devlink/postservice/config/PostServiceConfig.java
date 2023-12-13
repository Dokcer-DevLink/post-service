package com.goorm.devlink.postservice.config;

import com.goorm.devlink.postservice.util.MessageUtil;
import com.goorm.devlink.postservice.util.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostServiceConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public ModelMapperUtil modelMapperUtil(){
        return new ModelMapperUtil(modelMapper());
    }

    @Bean
    public MessageUtil messageUtil(MessageSource messageSource){return new MessageUtil(messageSource);}
}

