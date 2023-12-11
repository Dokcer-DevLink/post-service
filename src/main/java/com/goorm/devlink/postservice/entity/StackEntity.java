package com.goorm.devlink.postservice.entity;


import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
public class StackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stack_id")
    private Long id;

    @Column(name = "stack_name")
    private String stackName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    public static StackEntity getInstance(String stackName,PostEntity postEntity){

        return StackEntity.builder()
                .stackName(stackName)
                .post(postEntity)
                .build();
    }


}
