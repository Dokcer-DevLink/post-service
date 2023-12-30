package com.goorm.devlink.postservice.querydsl;

import com.goorm.devlink.postservice.entity.Address;
import com.goorm.devlink.postservice.util.KakaoAddressUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class KakaoAddressTest {

    @Autowired
    private KakaoAddressUtil kakaoAddressUtil;

    @Test
    public void kakaoAddressUtilTest(){
        Address address = kakaoAddressUtil.createAddress("");
//        System.out.println(address.getName());
//        System.out.println(address.getX());
//        System.out.println(address.getY());



    }


}
