package com.goorm.devlink.postservice.util;


import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;

import java.util.Locale;

@RequiredArgsConstructor
public class MessageUtil {

    private final MessageSource messageSource;

    public String getUserUuidEmptyMessage(){
        return getMessage("request.empty.userUuid");
    }
    public String getPostUuidEmptyMessage(){
        return getMessage("request.empty.postUuid");
    }
    public String getFeignErrorMessage(){ return getMessage("request.feign.connect");}

    public String getPostUuidNoSuchMessage(String postUuid){
        return getMessage("request.nosuchelement.postUuid",new String[]{postUuid});
    }

    public String getEnumTypeMisMatchMessage(String errorEnum, String errorValue){
        switch(errorEnum){
            case "postType" : return getMessage("request.typemismatch.postType",new String[]{errorValue});
            default: return getMessage("request.typemismatch");
        }
    }

    private String getMessage(String messageCode){
        return messageSource.getMessage(messageCode,new String[]{},Locale.KOREA);
    }

    private String getMessage(String messageCode, String[] parameters){
        return messageSource.getMessage(messageCode,parameters,Locale.KOREA);
    }





}
