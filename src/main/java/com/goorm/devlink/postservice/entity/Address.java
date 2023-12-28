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

    private String name;
    private double x;
    private double y;

    public static Address getInstance(String name,double x, double y){
        return Address.builder()
                .name(name)
                .x(x)
                .y(y)
                .build();
    }
}
