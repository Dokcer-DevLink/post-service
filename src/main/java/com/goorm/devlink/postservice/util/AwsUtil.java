package com.goorm.devlink.postservice.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.goorm.devlink.postservice.config.properties.vo.AwsConfigVo;
import com.goorm.devlink.postservice.vo.S3ImageVo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AwsUtil {
    private final AmazonS3Client amazonS3Client;
    private final AwsConfigVo awsConfigVo;


    public String savePostImageToS3Bucket(S3ImageVo s3ImageVo) {
        pushImageToS3Bucket(s3ImageVo,generateAwsMetaData(s3ImageVo));
        return getImageUrlFromBucket(s3ImageVo);
    }

    private ObjectMetadata generateAwsMetaData(S3ImageVo s3ImageVo){
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(s3ImageVo.getSize());
        metadata.setContentType(s3ImageVo.getContentType());
        return metadata;
    }

    private void pushImageToS3Bucket(S3ImageVo s3ImageVo, ObjectMetadata metadata){
        if(isFileExistInBucket(s3ImageVo)) deleteFileInBucket(s3ImageVo);
        putFileInBucket(s3ImageVo,metadata);
    }

    private String getImageUrlFromBucket(S3ImageVo S3ImageVo){
        return amazonS3Client.getUrl(awsConfigVo.getBucket(), S3ImageVo.getImageName()).toString();
    }

    private boolean isFileExistInBucket(S3ImageVo s3ImageVo){
        return (amazonS3Client.doesObjectExist(awsConfigVo.getBucket(),s3ImageVo.getImageName())) ? true : false;
    }

    private void deleteFileInBucket(S3ImageVo s3ImageVo){
        amazonS3Client.deleteObject(awsConfigVo.getBucket(),s3ImageVo.getImageName());
    }

    private void putFileInBucket(S3ImageVo s3ImageVo,ObjectMetadata metadata){
        amazonS3Client.putObject(
                awsConfigVo.getBucket(),
                s3ImageVo.getImageName(),
                s3ImageVo.getInputStream(),
                metadata);
    }


}
