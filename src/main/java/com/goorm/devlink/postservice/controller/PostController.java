package com.goorm.devlink.postservice.controller;


import com.goorm.devlink.postservice.vo.PostDetailRequest;
import com.goorm.devlink.postservice.vo.PostDetailResponse;
import com.goorm.devlink.postservice.vo.PostSimpleResponse;
import com.goorm.devlink.postservice.vo.PostStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {


    @GetMapping("/api/post/recommends")
    public ResponseEntity<List<PostSimpleResponse>> getRecommendPostList(@RequestParam String postType){

        return null;
    }

    @GetMapping("/api/post/list")
    public ResponseEntity<List<PostSimpleResponse>> getPostList(@RequestParam String postType, @RequestParam String keyWord){

        return null;
    }

    @GetMapping("/api/post")
    public ResponseEntity<PostDetailResponse> getPostDetail(@RequestParam String postUuid){
        return null;
    }

    @PostMapping("/api/post")
    public ResponseEntity<String> createPost(@RequestBody PostDetailRequest postDetailRequest){
        return null; // 생성된 postUuid
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
