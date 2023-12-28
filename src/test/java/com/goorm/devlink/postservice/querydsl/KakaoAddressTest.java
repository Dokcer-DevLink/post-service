package com.goorm.devlink.postservice.querydsl;

import com.goorm.devlink.postservice.util.KakaoAddressUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class KakaoAddressTest {

    @Autowired
    private KakaoAddressUtil kakaoAddressUtil;

    @Test
    public void kakaoAddressUtilTest(){
        List<String> addressList = kakaoAddressUtil.findAddressList("서울시 강북구");
        addressList.forEach(address ->{
            System.out.println(address);
        });
    }


}
