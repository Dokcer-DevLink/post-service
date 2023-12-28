package com.goorm.devlink.postservice.querydsl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.IOUtils;
import com.goorm.devlink.postservice.config.properties.vo.AwsConfigVo;
import com.goorm.devlink.postservice.util.AwsUtil;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.*;


@SpringBootTest
public class Base64ErrorTest {

    @Autowired
    private AwsUtil awsUtil;

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Autowired
    private AwsConfigVo awsConfigVo;


    @Test
    public void base64ImageTest() throws IOException {
        FileInputStream fis = new FileInputStream("/Users/kangmingu/stringtoolong.txt");
        String encodingImage = IOUtils.toString(fis);

        String[] arr = encodingImage.split(",");
        String data = arr[0]; // data:image/png;base64,
        String encoding = arr[1]; // 인코딩 데이터
        String contentType = data.substring(data.indexOf(":") + 1, data.indexOf(";")); // image/png 추출

        System.out.println(encoding);
        byte[] decodedImage = Base64.decodeBase64(encoding);

        InputStream bis = new ByteArrayInputStream(decodedImage);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(decodedImage.length);
        metadata.setContentType(contentType);
        String name = "post/"+ "11111111";

        amazonS3Client.putObject(awsConfigVo.getBucket(), name, bis, metadata);
        String url = amazonS3Client.getUrl(awsConfigVo.getBucket(), name).toString();
        System.out.println("url :" + url);

        // 1. post / profile 구분 ( 해결 )
        // 2. png,jpg,jpeg 만 가능하도록, 다른 파일형식 들어오면 에러처리 ( 요청으로 들어온 데이터 전처리하기 )
        // 3. 파일이름 post/postUuid, profile/profileUuid ( 해결 )
        // 4. 수정시, 기존 이미지 삭제 후 저장하기

    }


}
