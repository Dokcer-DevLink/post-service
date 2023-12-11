package com.goorm.devlink.postservice.entity;


import com.goorm.devlink.postservice.vo.PostStatus;
import com.goorm.devlink.postservice.vo.PostType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor( access = AccessLevel.PROTECTED)
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id;
    @Column(name = "post_title")
    private String  postTitle;
    @Column(name = "post_content")
    private String postContent;
    @Column(name = "user_uuid")
    private String userUuid;
    @Column(name = "post_uuid")
    private String postUuid;
    @Column(name = "post_type")
    @Enumerated(EnumType.STRING)
    private PostType postType;
    @Column(name = "post_status")
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;
    @Column(name = "post_image_url")
    private String postImageUrl;
    @Column(name = "address")
    private String address;
    @Column(name = "running_time")
    private int runningTime;
    @Column(name = "isDeleted")
    private boolean isDeleted;

    @OneToMany(mappedBy = "post")
    private List<StackEntity> stackEntityList;



}
