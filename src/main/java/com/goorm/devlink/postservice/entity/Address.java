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
    private Double addressX;
    private Double addressY;

    public static Address getInstance(String name,Double x, Double y){
        return Address.builder()
                .addressName(name)
                .addressX(x)
                .addressY(y)
                .build();
    }
}
