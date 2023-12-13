package com.goorm.devlink.postservice.config.properties.vo;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;


@Slf4j
@RequiredArgsConstructor
@Getter
public class PageConfigVo {

    private final int offset;
    private final int size;
    private final String orderBy;

}
