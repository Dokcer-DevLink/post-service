package com.goorm.devlink.postservice.controller;

import com.goorm.devlink.postservice.dto.PostBasicDto;
import com.goorm.devlink.postservice.entity.Address;
import com.goorm.devlink.postservice.feign.ProfileServiceClient;
import com.goorm.devlink.postservice.service.PostService;
import com.goorm.devlink.postservice.util.MessageUtil;
import com.goorm.devlink.postservice.vo.*;
import com.goorm.devlink.postservice.vo.request.PostCreateRequest;
import com.goorm.devlink.postservice.vo.request.PostEditRequest;
import com.goorm.devlink.postservice.vo.request.PostMatchingRequest;
import com.goorm.devlink.postservice.vo.request.PostStatusRequest;
import com.goorm.devlink.postservice.vo.response.PostCommentResponse;
import com.goorm.devlink.postservice.vo.response.PostDetailResponse;
import com.goorm.devlink.postservice.vo.response.PostMatchingResponse;
import com.goorm.devlink.postservice.vo.response.PostSimpleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
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
    public ResponseEntity<Page<PostSimpleResponse>> getRecommendPostList(@RequestParam PostType postType, @RequestHeader HttpHeaders headers){
        String userUuid = headers.getFirst("userUuid");
        List<String> userStacks = getStackList(userUuid); // 서비스 간 통신
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
    public ResponseEntity<PostCommentResponse> createPost(@RequestBody @Valid PostCreateRequest postCreateRequest,
                                                          @RequestHeader("userUuid") String userUuid){
        if(userUuid.isEmpty()) { throw new NoSuchElementException(messageUtil.getUserUuidEmptyMessage());}
        String postUuid = UUID.randomUUID().toString();
        String imageUrl = getImageUrl(postCreateRequest.getPostImage(),postUuid);
        Address address = getAddress(postCreateRequest.getAddress());
        postService.createPost(PostBasicDto.getInstanceForCreate(postCreateRequest,postUuid,imageUrl,userUuid,address));
        return ResponseEntity.ok(PostCommentResponse.getInstanceForCreate(postUuid));
    }

    // 포스트 수정하기 ( 포스트 상세 페이지 )
    @PutMapping("/api/post")
    public ResponseEntity<PostCommentResponse> editPost(@RequestBody @Valid PostEditRequest postEditRequest,
                                                        @RequestHeader("userUuid") String userUuid){
        if(userUuid.isEmpty()) { throw new NoSuchElementException(messageUtil.getUserUuidEmptyMessage());}
        String imageUrl = getImageUrl(postEditRequest.getPostImage(),postEditRequest.getPostUuid());
        Address address = getAddress(postEditRequest.getAddress());
        postService.editPost(PostBasicDto.getInstanceForEdit(postEditRequest,address,imageUrl));
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

    // 매칭 데이터 전달 ( 매칭 서비스 - 포스트 서비스 )
    @GetMapping("/api/post/match")
    public ResponseEntity<List<PostMatchingResponse>> getPostMatchingData(@RequestBody PostMatchingRequest postMatchingRequest){
        return ResponseEntity.ok(postService.getPostMatchingData(postMatchingRequest));
    }

    private List<String> getStackList(String userUuid){
        return (userUuid!=null && !userUuid.isEmpty()) ?
                profileServiceClient.viewUserStackList(userUuid).getBody() : null;
    }

    private String getImageUrl(String encodingImage, String postUuid){
        if(S3ImageVo.isNullOrEmpty(encodingImage)) return null;
        if(S3ImageVo.isNotValidContents(encodingImage)) {
            throw new IllegalArgumentException(messageUtil.getImageContentErrorMessage()); }
        if(S3ImageVo.isNotValidType(encodingImage)) {
            throw new IllegalArgumentException(messageUtil.getImageTypeErrorMessage()); }

        S3ImageVo s3ImageVo = S3ImageVo.getInstance(encodingImage,postUuid);
        return postService.savePostImageToS3Bucket(s3ImageVo);

    }

    private Address getAddress(String address){
        return (address != null && !address.isEmpty()) ? postService.createAddress(address) : null;
    }


}
