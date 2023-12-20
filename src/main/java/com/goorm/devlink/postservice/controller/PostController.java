package com.goorm.devlink.postservice.controller;

import com.goorm.devlink.postservice.dto.PostBasicDto;
import com.goorm.devlink.postservice.service.PostService;
import com.goorm.devlink.postservice.util.MessageUtil;
import com.goorm.devlink.postservice.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
//@CrossOrigin("*") // 임시로 모든 도메인 CORS 허용
public class PostController {

    private final PostService postService;
    private final MessageUtil messageUtil;

    @GetMapping("/api/post/test")
    public String test(@RequestParam String postType){
        return "OK";
    }

    // 추천 멘토 포스트, 멘티 포스트 조회
    @GetMapping("/api/post/recommend")
    public ResponseEntity<Page<PostSimpleResponse>> getRecommendPostList(@RequestParam PostType postType){
        // 프로필 서비스에서 스택리스트 가져오는 로직 필요
        List<String> tmpStacks = getTmpProfileStackList(); // 임시 프로필 데이터
        // stack List
        return new ResponseEntity<>(postService.getRecommendPostList(postType,tmpStacks),HttpStatus.OK);
    }

    // 포스트 리스트 조회 ( 포스트 검색 페이지 )
    @GetMapping("/api/post/list")
    public ResponseEntity<Page<PostSimpleResponse>> getPostList(@RequestParam PostType postType,
                                                                @RequestParam String keyword){
        return new ResponseEntity<>(postService.getPostList(postType,keyword), HttpStatus.OK);
    }

    // 포스트 리스트 조회 ( 마이페이지 )
    @GetMapping("/api/post/my")
    public ResponseEntity<Page<PostSimpleResponse>> getMyPostList(@RequestParam PostType postType,
                                                                  @RequestHeader("userUuid") String userUuid){
        if(userUuid.isEmpty()) { throw new NoSuchElementException(messageUtil.getUserUuidEmptyMessage());}
        return new ResponseEntity<>(postService.getMyPostList(postType,userUuid), HttpStatus.OK);
    }

    //포스트 상세 조회 ( 상세페이지 )
    @GetMapping("/api/post")
    public ResponseEntity<PostDetailResponse> getPostDetail(@RequestParam String postUuid){
        if(postUuid.isEmpty()) { throw new NoSuchElementException(messageUtil.getPostUuidEmptyMessage());}
        return new ResponseEntity<>(postService.getDetailPost(postUuid),HttpStatus.OK);
    }

    // 포스트 생성하기
    @PostMapping("/api/post")
    public ResponseEntity<PostCommentResponse> createPost(@RequestPart @Valid PostCreateRequest postCreateRequest,
                                                          @RequestPart("postImage") MultipartFile postImage,
                                                          @RequestHeader("userUuid") String userUuid){
        //멀티파트파일 empty 체크
        if(userUuid.isEmpty()) { throw new NoSuchElementException(messageUtil.getUserUuidEmptyMessage());}
        String postImageUrl = postService.savePostImageToS3Bucket(postImage);
        String postUuid = postService.createPost(PostBasicDto.getInstanceForCreate(postCreateRequest,postImageUrl,userUuid));
        return new ResponseEntity<>(PostCommentResponse.getInstanceForCreate(postUuid),HttpStatus.CREATED);
    }

    // 포스트 수정하기 ( 포스트 상세 페이지 )
    @PutMapping("/api/post")
    public ResponseEntity<PostCommentResponse> editPost(@RequestBody @Valid PostEditRequest postEditRequest){
        postService.editPost(PostBasicDto.getInstanceForEdit(postEditRequest));
        PostCommentResponse responseEdit = PostCommentResponse.getInstanceForEdit(postEditRequest.getPostUuid());
        return new ResponseEntity<>(responseEdit, HttpStatus.OK);
    }

    // 포스트 삭제하기
    @DeleteMapping("/api/post")
    public ResponseEntity<PostCommentResponse> deletePost(@RequestParam String postUuid){
        if(postUuid.isEmpty()) { throw new NoSuchElementException(messageUtil.getPostUuidEmptyMessage());}
        postService.deletePost(postUuid);
        PostCommentResponse responseDelete = PostCommentResponse.getInstanceForDelete(postUuid);
        return new ResponseEntity<>(responseDelete,HttpStatus.OK);
    }

    // 포스트 상테 업데이트
    @PutMapping("/api/post/status")
    public ResponseEntity<PostCommentResponse> updatePostStatus(@RequestBody @Valid PostStatusRequest postStatusRequest){
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
