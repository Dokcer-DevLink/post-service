package com.goorm.devlink.postservice.controller;

import com.goorm.devlink.postservice.dto.PostBasicDto;
import com.goorm.devlink.postservice.feign.ProfileServiceClient;
import com.goorm.devlink.postservice.service.PostService;
import com.goorm.devlink.postservice.util.MessageUtil;
import com.goorm.devlink.postservice.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
//@CrossOrigin("*") // 임시로 모든 도메인 CORS 허용
public class PostController {

    private final PostService postService;
    private final MessageUtil messageUtil;
    private final ProfileServiceClient profileServiceClient;

    @GetMapping("/api/post/test")
    public String test(@RequestParam String postType){
        return "OK";
    }

    // 추천 멘토 포스트, 멘티 포스트 조회
    @GetMapping("/api/post/recommend")
    public ResponseEntity<Page<PostSimpleResponse>> getRecommendPostList(@RequestParam PostType postType,@RequestHeader("userUuid") String userUuid){
        if(userUuid.isEmpty()) { throw new NoSuchElementException(messageUtil.getUserUuidEmptyMessage());}
        List<String> userStacks = profileServiceClient.viewUserStackList(userUuid); // 서비스 간 통신
        return ResponseEntity.ok(postService.getRecommendPostList(postType,userStacks));
    }

    // 포스트 리스트 조회 ( 포스트 검색 페이지 )
    @GetMapping("/api/post/list")
    public ResponseEntity<Page<PostSimpleResponse>> getPostList(@RequestParam PostType postType,
                                                                @RequestParam String keyword){
        return ResponseEntity.ok(postService.getPostList(postType,keyword));
    }

    // 포스트 리스트 조회 ( 마이페이지 )
    @GetMapping("/api/post/my")
    public ResponseEntity<Page<PostSimpleResponse>> getMyPostList(@RequestParam PostType postType,
                                                                  @RequestHeader("userUuid") String userUuid){
        if(userUuid.isEmpty()) { throw new NoSuchElementException(messageUtil.getUserUuidEmptyMessage());}
        return ResponseEntity.ok(postService.getMyPostList(postType,userUuid));
    }

    //포스트 상세 조회 ( 상세페이지 )
    @GetMapping("/api/post")
    public ResponseEntity<PostDetailResponse> getPostDetail(@RequestParam String postUuid){
        if(postUuid.isEmpty()) { throw new NoSuchElementException(messageUtil.getPostUuidEmptyMessage());}
        return ResponseEntity.ok(postService.getDetailPost(postUuid));
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
        return ResponseEntity.ok(PostCommentResponse.getInstanceForCreate(postUuid));
    }

    // 포스트 수정하기 ( 포스트 상세 페이지 )
    @PutMapping("/api/post")
    public ResponseEntity<PostCommentResponse> editPost(@RequestBody @Valid PostEditRequest postEditRequest){
        postService.editPost(PostBasicDto.getInstanceForEdit(postEditRequest));
        PostCommentResponse responseEdit = PostCommentResponse.getInstanceForEdit(postEditRequest.getPostUuid());
        return ResponseEntity.ok(responseEdit);
    }

    // 포스트 삭제하기
    @DeleteMapping("/api/post")
    public ResponseEntity<PostCommentResponse> deletePost(@RequestParam String postUuid){
        if(postUuid.isEmpty()) { throw new NoSuchElementException(messageUtil.getPostUuidEmptyMessage());}
        postService.deletePost(postUuid);
        PostCommentResponse responseDelete = PostCommentResponse.getInstanceForDelete(postUuid);
        return ResponseEntity.ok(responseDelete);
    }

    // 포스트 상테 업데이트
    @PutMapping("/api/post/status")
    public ResponseEntity<PostCommentResponse> updatePostStatus(@RequestBody @Valid PostStatusRequest postStatusRequest){
        String postUuid = postService.updateStatus(postStatusRequest);
        PostCommentResponse responseUpdate = PostCommentResponse.getInstanceForUpdate(postUuid);
        return ResponseEntity.ok(responseUpdate);
    }

}
