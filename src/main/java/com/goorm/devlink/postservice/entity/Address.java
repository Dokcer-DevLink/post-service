package com.goorm.devlink.postservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Address {

    private String addressName;
    private double addressX;
    private double addressY;

    public static Address getInstance(String name,double x, double y){
        return Address.builder()
                .addressName(name)
                .addressX(x)
                .addressY(y)
                .build();
    }
}
