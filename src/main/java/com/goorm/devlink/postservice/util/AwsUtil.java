package com.goorm.devlink.postservice.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.goorm.devlink.postservice.config.properties.vo.AwsConfigVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
public class AwsUtil {
    private final AmazonS3Client amazonS3Client;
    private final AwsConfigVo awsConfigVo;


    public String savePostImageToS3Bucket(MultipartFile postImage) {
        pushImageToS3Bucket(postImage,generateAwsMetaData(postImage));
        return getImageUrlFromBucket(postImage);
    }

    private ObjectMetadata generateAwsMetaData(MultipartFile postImage){
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(postImage.getSize());
        metadata.setContentType(postImage.getContentType());
        return metadata;
    }

    private void pushImageToS3Bucket(MultipartFile postImage, ObjectMetadata metadata){
        try {
            amazonS3Client.putObject(awsConfigVo.getBucket(),
                    postImage.getOriginalFilename(), postImage.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    private String getImageUrlFromBucket(MultipartFile postImage){
        return amazonS3Client.getUrl(awsConfigVo.getBucket(), postImage.getOriginalFilename()).toString();
    }


}
