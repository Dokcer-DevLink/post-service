package com.goorm.devlink.postservice.entity;


import com.goorm.devlink.postservice.dto.PostBasicDto;
import com.goorm.devlink.postservice.vo.OnOffline;
import com.goorm.devlink.postservice.vo.PostStatus;
import com.goorm.devlink.postservice.vo.PostType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor( access = AccessLevel.PROTECTED)
public class PostEntity extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "post_title")
    private String  postTitle;

    @Lob
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

    @Embedded
    private Address address;

    @Column(name = "unit_time_count")
    private Integer unitTimeCount;

    @Column(name = "isDeleted")
    private boolean isDeleted;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "post_stack", joinColumns = @JoinColumn(name = "post_id"))
    private List<String> stacks = new ArrayList<>();

    public void updateStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }

    public void update(PostBasicDto postBasicDto){
        this.setPostTitle(Optional.ofNullable(postBasicDto.getPostTitle()).orElse(postTitle));
        this.setPostImageUrl(Optional.ofNullable(postBasicDto.getPostImageUrl()).orElse(postImageUrl));
        this.setPostContent(Optional.ofNullable(postBasicDto.getPostContent()).orElse(postContent));
        this.setStacks(Optional.ofNullable(postBasicDto.getStacks()).orElse(stacks));
        this.setOnOffline(Optional.ofNullable(postBasicDto.getOnOffline()).orElse(onOffline));
        this.setPostType(Optional.ofNullable(postBasicDto.getPostType()).orElse(postType));
        this.setAddress(Optional.ofNullable(postBasicDto.getAddress()).orElse(address));
        this.setPostStatus(Optional.ofNullable(postBasicDto.getPostStatus()).orElse(postStatus));
        this.setUnitTimeCount(Optional.ofNullable(postBasicDto.getUnitTimeCount()).orElse(unitTimeCount));
    }


}
