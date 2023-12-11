package com.goorm.devlink.postservice.controller;


import com.goorm.devlink.postservice.service.PostService;
import com.goorm.devlink.postservice.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 추천 멘토 포스트, 멘티 포스트 조회
    @GetMapping("/api/post/recommends")
    public ResponseEntity<List<PostSimpleResponse>> getRecommendPostList(@RequestParam String postType){
        // 프로필 서비스에서 프로필 데이터 가져와야 함.
        return null;
    }

    // 포스트 리스트 조회 ( 포스트 검색 페이지 )
    @GetMapping("/api/post/list")
    public ResponseEntity<List<PostSimpleResponse>> getPostList(@RequestParam @NotBlank PostType postType,
                                                                @RequestParam String keyword){
        return new ResponseEntity<>(postService.getPostList(postType,keyword), HttpStatus.OK);
    }

    // 포스트 리스트 조회 ( 마이페이지 )
    @GetMapping("/api/post/my")
    public ResponseEntity<List<PostSimpleResponse>> getMyPostList(@RequestParam String postType, @RequestHeader("userUuid") String userUuid){

        return null;
    }

    @GetMapping("/api/post")
    public ResponseEntity<PostDetailResponse> getPostDetail(@RequestParam String postUuid){
        return null;
    }

    @PostMapping("/api/post")
    public ResponseEntity<PostCreateResponse> createPost(@RequestBody PostDetailRequest postDetailRequest){
        String postUuid = postService.createPost(postDetailRequest);
        return new ResponseEntity<>(PostCreateResponse.getInstance(postUuid),HttpStatus.CREATED);
    }

    @PutMapping("/api/post")
    public void editPost(@RequestBody PostDetailRequest postDetailRequest){
    }

    @DeleteMapping("/api/post")
    public void deletePost(@RequestParam String postUuid){

    }

    @PutMapping("/api/post/status")
    public void editPostStatus(@RequestBody PostStatusRequest postStatusRequest){

    }








}
