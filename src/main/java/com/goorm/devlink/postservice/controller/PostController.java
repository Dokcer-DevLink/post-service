package com.goorm.devlink.postservice.controller;


import com.goorm.devlink.postservice.dto.PostBasicDto;
import com.goorm.devlink.postservice.service.PostService;
import com.goorm.devlink.postservice.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*") // 임시로 모든 도메인 CORS 허용
public class PostController {

    private final PostService postService;

    // 추천 멘토 포스트, 멘티 포스트 조회
    @GetMapping("/api/post/recommend")
    public ResponseEntity<Page<PostSimpleResponse>> getRecommendPostList(@RequestParam @NotBlank PostType postType){
        // 프로필 서비스에서 프로필에 설정된 스택 리스트 가져와야 함.
        // stack List
        return new ResponseEntity<>(postService.getRecommendPostList(postType,getTmpProfileStackList()),HttpStatus.OK);
    }

    // 포스트 리스트 조회 ( 포스트 검색 페이지 )
    @GetMapping("/api/post/list")
    public ResponseEntity<Page<PostSimpleResponse>> getPostList(@RequestParam @NotBlank PostType postType,
                                                                @RequestParam String keyword){
        return new ResponseEntity<>(postService.getPostList(postType,keyword), HttpStatus.OK);
    }

    // 포스트 리스트 조회 ( 마이페이지 )
    @GetMapping("/api/post/my")
    public ResponseEntity<Page<PostSimpleResponse>> getMyPostList(@RequestParam String postType, @RequestHeader("userUuid") String userUuid){
        return new ResponseEntity<>(postService.getMyPostList(userUuid), HttpStatus.OK);
    }

    //포스트 리스트 조회 ( 상세페이지 )
    @GetMapping("/api/post")
    public ResponseEntity<PostDetailResponse> getPostDetail(@RequestParam String postUuid){
        return new ResponseEntity<>(postService.getDetailPost(postUuid),HttpStatus.OK);
    }

    // 포스트 생성하기
    @PostMapping("/api/post")
    public ResponseEntity<PostCommentResponse> createPost(@RequestBody PostDetailRequest postDetailRequest,
                                                          @RequestHeader("userUuid") @NotBlank String userUuid){
        String postUuid = postService.createPost(PostBasicDto.getInstanceForCreate(postDetailRequest,userUuid));
        return new ResponseEntity<>(PostCommentResponse.getInstanceForCreate(postUuid),HttpStatus.CREATED);
    }

    // 포스트 수정하기 ( 포스트 상세 페이지 )
    @PutMapping("/api/post")
    public ResponseEntity<PostCommentResponse> editPost(@RequestBody PostDetailRequest postDetailRequest){
        postService.editPost(PostBasicDto.getInstanceForEdit(postDetailRequest));
        PostCommentResponse responseEdit = PostCommentResponse.getInstanceForEdit(postDetailRequest.getPostUuid());
        return new ResponseEntity<>(responseEdit, HttpStatus.OK);
    }

    // 포스트 삭제하기
    @DeleteMapping("/api/post")
    public ResponseEntity<PostCommentResponse> deletePost(@RequestParam String postUuid){
        postService.deletePost(postUuid);
        PostCommentResponse responseDelete = PostCommentResponse.getInstanceForDelete(postUuid);
        return new ResponseEntity<>(responseDelete,HttpStatus.OK);
    }

    // 포스트 상세 업데이트
    @PutMapping("/api/post/status")
    public ResponseEntity<PostCommentResponse> updatePostStatus(@RequestBody PostStatusRequest postStatusRequest){
        String postUuid = postService.updateStatus(postStatusRequest);
        PostCommentResponse responseUpdate = PostCommentResponse.getInstanceForUpdate(postUuid);
        return new ResponseEntity<>(responseUpdate,HttpStatus.OK);

    }

    // 임시 프로필에서 가져온 스택리스트 데이터
    private List<String> getTmpProfileStackList(){
        List<String> stacks = new ArrayList<>();
        stacks.add("Spring");
        stacks.add("K8S");
        stacks.add("AWS");
        return stacks;
    }


}
