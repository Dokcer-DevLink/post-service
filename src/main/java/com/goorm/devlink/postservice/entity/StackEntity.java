package com.goorm.devlink.postservice.entity;


import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter @Setter
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


    public static StackEntity getInstanceTest(String stackName,PostEntity postEntity){
        StackEntity stackEntity = new StackEntity();
        stackEntity.setStackName(stackName);
        stackEntity.setPost(postEntity);
        return stackEntity;
    }


}
