package com.goorm.devlink.postservice.entity;


import com.goorm.devlink.postservice.vo.PostStatus;
import com.goorm.devlink.postservice.vo.PostType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
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
    @Column(name = "post_uuid",unique = true)
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

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "stack", joinColumns = @JoinColumn(name = "post_id"))
    private List<String> stacks = new ArrayList<>();

    public void updateForMerge(long postId) {
        id = postId;
    }

    // 테스트용 메소드
    public static PostEntity getInstanceTest(int i,PostType postType, String userUuid,List<String> stacks){
        PostEntity postEntity = new PostEntity();
        postEntity.setPostTitle("Title"+i);
        postEntity.setPostContent(("Content"+i));
        postEntity.setUserUuid(userUuid);
        postEntity.setPostUuid(UUID.randomUUID().toString());
        postEntity.setPostType(PostType.MENTOR);
        postEntity.setPostStatus(PostStatus.WAITING);
        postEntity.setPostImageUrl("default_image_url");
        postEntity.setAddress("address"+i);
        postEntity.setRunningTime(30);
        postEntity.setDeleted(false);
        postEntity.stacks = stacks;

        return postEntity;
    }



}
