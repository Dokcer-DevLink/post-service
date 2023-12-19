package com.goorm.devlink.postservice.entity;


import com.goorm.devlink.postservice.vo.OnOffline;
import com.goorm.devlink.postservice.vo.PostStatus;
import com.goorm.devlink.postservice.vo.PostType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor( access = AccessLevel.PROTECTED)
public class PostEntity extends BaseTimeEntity{

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
    @Column(name = "post_uuid",unique = true)
    private String postUuid;
    @Column(name = "post_type")
    @Enumerated(EnumType.STRING)
    private PostType postType;
    @Column(name = "post_status")
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;
    @Column(name = "onOffline")
    @Enumerated(EnumType.STRING)
    private OnOffline onOffline;
    @Column(name = "post_image_url")
    private String postImageUrl;
    @Column(name = "address")
    private String address;
    @Column(name = "running_time")
    private int runningTime;
    @Column(name = "isDeleted")
    private boolean isDeleted;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "stack", joinColumns = @JoinColumn(name = "post_id"))
    private List<String> stacks = new ArrayList<>();

    public void updateForMerge(long postId) {
        id = postId;
    }
    public void updateStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }


}
