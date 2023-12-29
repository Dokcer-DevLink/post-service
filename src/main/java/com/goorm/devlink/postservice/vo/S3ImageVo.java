package com.goorm.devlink.postservice.vo;


import com.amazonaws.util.Base64;
import lombok.Builder;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Getter
@Builder
public class S3ImageVo {

    public static final String DEFAULT_URL = "DEFAULT_URL";
    private static final String FOLDER_NAME = "post/";
    private String contentType;
    private byte[] content;
    private long size;
    private String imageName;
    private InputStream inputStream;
    public static S3ImageVo getInstance(String encodingData,String postUuid){
        String[] arr = encodingData.split(",");
        byte[] content = Base64.decode(arr[1]);
        String contentType = arr[0].substring(arr[0].indexOf(":") + 1, arr[0].indexOf(";"));

        return S3ImageVo.builder()
                .contentType(contentType)
                .content(content)
                .size(content.length)
                .imageName(FOLDER_NAME.concat(postUuid))
                .inputStream(new ByteArrayInputStream(content))
                .build();
    }

    public static boolean isNullOrEmpty(String encodingData){
        return (encodingData!=null&&!encodingData.isEmpty())? true : false;
    }

    public static boolean isValidContents(String encodingData){
        return encodingData.contains(",");
    }

    public static boolean isValidType(String encodingData){
        String[] arr = encodingData.split(",");
        String contentType = arr[0].substring(arr[0].indexOf(":") + 1, arr[0].indexOf(";"));

        return (    S3ImageType.PNG.getContentType().equals(contentType) ||
                    S3ImageType.JPG.getContentType().equals(contentType) ||
                    S3ImageType.JPEG.getContentType().equals(contentType)
                ) ? true : false;
    }

    @Getter
    public enum S3ImageType{
        PNG("image/png"),
        JPG("image/jpg"),
        JPEG("image/jpeg");

        private String contentType;

        S3ImageType(String contentType){
            this.contentType = contentType;
        }
    }

}
