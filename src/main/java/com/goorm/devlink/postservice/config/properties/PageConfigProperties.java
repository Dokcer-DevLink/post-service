package com.goorm.devlink.postservice.config.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import javax.validation.constraints.NotBlank;

@ConfigurationProperties("data.page")
@Getter @Setter
public class PageConfigProperties {

    @NotBlank
    private int offset;
    @NotBlank
    private int size;
    @NotBlank
    private String orderBy;

}
