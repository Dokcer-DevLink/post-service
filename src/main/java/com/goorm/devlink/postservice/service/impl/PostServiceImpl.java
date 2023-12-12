package com.goorm.devlink.postservice.service.impl;
import com.goorm.devlink.postservice.dto.PostBasicDto;
import com.goorm.devlink.postservice.entity.PostEntity;
import com.goorm.devlink.postservice.repository.PostRepository;
import com.goorm.devlink.postservice.service.PostService;
import com.goorm.devlink.postservice.util.ModelMapperUtil;
import com.goorm.devlink.postservice.vo.PostDetailResponse;
import com.goorm.devlink.postservice.vo.PostSimpleResponse;
import com.goorm.devlink.postservice.vo.PostStatusRequest;
import com.goorm.devlink.postservice.vo.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapperUtil modelMapperUtil;

    @Override
    public String createPost(PostBasicDto postBasicDto) {
        PostEntity postEntity = modelMapperUtil.convertToPostEntity(postBasicDto);
        postRepository.save(postEntity);
        return postEntity.getPostUuid();
    }

    @Override
    public Page<PostSimpleResponse> getPostList(PostType postType, String keyword) {
        PageRequest pageRequest = PageRequest.of(0,5);
        Page<PostEntity> postPage = postRepository.findPostListByPostTypeAndKeyWord(postType, keyword, pageRequest);
        return postPage.map( post -> PostSimpleResponse.getInstance(post) );
    }

    @Override
    public void editPost(PostBasicDto instanceForEdit) {
        Optional<PostEntity> optionalPost
                = Optional.of(postRepository.findByPostUuid(instanceForEdit.getPostUuid()));
        optionalPost.orElseThrow(()-> { throw new NoSuchElementException();});
        PostEntity postEntity = modelMapperUtil.convertToPostEntity(instanceForEdit);
        postEntity.updateForMerge(optionalPost.get().getId());
        postRepository.save(postEntity);
    }

    @Override
    public void deletePost(String postUuid) {
        Optional<PostEntity> optionalPost = Optional.of(postRepository.findByPostUuid(postUuid));
        postRepository.delete(optionalPost.orElseThrow(()->{ throw new NoSuchElementException();}));
    }

    @Override
    public PostDetailResponse getDetailPost(String postUuid) {
        Optional<PostEntity> optionalPost = Optional.of(postRepository.findByPostUuid(postUuid));
        optionalPost.orElseThrow(()-> { throw new NoSuchElementException();});
        return modelMapperUtil.convertToPostDetailResponse(optionalPost.get());
    }

    @Override
    public String updateStatus(PostStatusRequest postStatusRequest) {
        Optional<PostEntity> optionalPost = Optional.of(postRepository.findByPostUuid(postStatusRequest.getPostUuid()));
        optionalPost.orElseThrow(()-> { throw new NoSuchElementException();});
        optionalPost.get().updateStatus(postStatusRequest.getPostStatus());
        postRepository.save(optionalPost.get());
        return postStatusRequest.getPostUuid();
    }
}
