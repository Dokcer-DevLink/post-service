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
    private List<StackEntity> stacks = new ArrayList<>();


    public List<String> getStackNames(){
        List<String> stackNames = new ArrayList<>();
        for (StackEntity stack : stacks) {
            stackNames.add(stack.getStackName());
        }
        return stackNames;
    }

    // 테스트용 메소드
    public static PostEntity getInstanceTest(int i,PostType postType, String userUuid){
        PostEntity postEntity = new PostEntity();
        postEntity.setPostTitle("Spring"+i);
        postEntity.setPostContent(("Content"+i));
        postEntity.setUserUuid(userUuid);
        postEntity.setPostUuid(UUID.randomUUID().toString());
        postEntity.setPostType(PostType.MENTOR);
        postEntity.setPostStatus(PostStatus.WAITING);
        postEntity.setPostImageUrl("default_image_url");
        postEntity.setAddress("address"+i);
        postEntity.setRunningTime(30);
        postEntity.setDeleted(false);

        return postEntity;
    }





}
