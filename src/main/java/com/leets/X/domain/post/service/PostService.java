package com.leets.X.domain.post.service;

import com.leets.X.domain.image.domain.Image;
import com.leets.X.domain.image.service.ImageService;
import com.leets.X.domain.like.domain.Like;
import com.leets.X.domain.like.repository.LikeRepository;
import com.leets.X.domain.post.controller.ResponseMessage;
import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.domain.enums.IsDeleted;
import com.leets.X.domain.post.dto.request.PostRequestDTO;
import com.leets.X.domain.post.dto.response.ParentPostResponseDto;
import com.leets.X.domain.post.dto.response.PostResponseDto;
import com.leets.X.domain.post.dto.response.PostUserResponse;
import com.leets.X.domain.post.exception.AlreadyLikedException;
import com.leets.X.domain.post.exception.NotLikedException;
import com.leets.X.domain.post.exception.PostNotFoundException;
import com.leets.X.domain.post.exception.UnauthorizedPostDeletionException;
import com.leets.X.domain.post.repository.PostRepository;
import com.leets.X.domain.user.domain.User;
import com.leets.X.domain.user.exception.UserNotFoundException;
import com.leets.X.domain.user.service.UserService;
import com.leets.X.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final LikeRepository likeRepository;

    // 모든 부모 글만 조회 (자식 글 제외)
    public List<ParentPostResponseDto> getAllParentPosts(String email) {
        User user = userService.find(email);

        List<Post> posts = postRepository.findByParentIsNullAndIsDeletedOrderByCreatedAtDesc(IsDeleted.ACTIVE);

        return posts.stream()
                .map(post -> {
                    boolean isLikedByUser = likeRepository.existsByPostAndUser(post, user);
                    return ParentPostResponseDto.from(post, isLikedByUser);
                })
                .collect(Collectors.toList());
    }

    // 전체 게시물 조회 (자식 글 포함)
    public PostResponseDto getPostResponse(Long id, String email) {
        Post post = postRepository.findWithRepliesByIdAndIsDeleted(id, IsDeleted.ACTIVE)
                .orElseThrow(PostNotFoundException::new);

        User user = userService.find(email);
        boolean isLikedByUser = likeRepository.existsByPostAndUser(post, user);

        return PostResponseDto.from(post, isLikedByUser);
    }

    // 좋아요 순으로 게시물 조회
    public List<PostResponseDto> getPostsSortedByLikes(String email) {
        User user = userService.find(email);
        List<Post> posts = postRepository.findAll();

        return posts.stream()
                .filter(post -> post.getIsDeleted() == IsDeleted.ACTIVE)
                .sorted(Comparator.comparing(Post::getLikesCount).reversed())
                .map(post -> {
                    boolean isLikedByUser = likeRepository.existsByPostAndUser(post, user);
                    return PostResponseDto.from(post, isLikedByUser);
                })
                .collect(Collectors.toList());
    }

    // 최신 부모 글 10개 조회 (자식 글 제외)
    public List<ParentPostResponseDto> getLatestParentPosts(String email) {
        User user = userService.find(email);

        List<Post> posts = postRepository.findByParentIsNullAndIsDeletedOrderByCreatedAtDesc(IsDeleted.ACTIVE)
                .stream()
                .limit(10)
                .collect(Collectors.toList());

        return posts.stream()
                .map(post -> {
                    boolean isLikedByUser = likeRepository.existsByPostAndUser(post, user);
                    return ParentPostResponseDto.from(post, isLikedByUser);
                })
                .collect(Collectors.toList());
    }

    // 글 생성
    @Transactional
    public PostResponseDto createPost(PostRequestDTO postRequestDTO, String email) {
        User user = userService.find(email);
        if (user == null) {
            throw new UserNotFoundException();
        }

        Post post = Post.create(user, postRequestDTO.content(), null);
        Post savedPost = postRepository.save(post);

        return PostResponseDto.from(savedPost, false);
    }

    // 좋아요 추가
    @Transactional
    public String addLike(Long postId, String email) {
        Post post = findPost(postId);
        User user = userService.find(email);

        if (likeRepository.existsByPostAndUser(post, user)) {
            throw new AlreadyLikedException();
        }

        likeRepository.save(new Like(post, user));
        updateLikeCount(post);

        return "좋아요가 추가되었습니다. 현재 좋아요 수: " + post.getLikesCount();
    }

    // 답글 생성
    @Transactional
    public PostResponseDto createReply(Long parentId, PostRequestDTO postRequestDTO, String email) {
        User user = userService.find(email);
        Post parentPost = findPost(parentId);

        Post reply = Post.create(user, postRequestDTO.content(), parentPost);
        Post savedReply = postRepository.save(reply);

        return PostResponseDto.from(savedReply, false);
    }

    // 게시물 삭제
    @Transactional
    public void deletePost(Long postId, String email) {
        Post post = findPost(postId);
        User user = userService.find(email);

        // 게시물의 소유자인지 확인
        if (!post.getUser().equals(user)) {
            throw new UnauthorizedPostDeletionException();
        }

        post.delete();
    }

    // 좋아요 취소
    @Transactional
    public String cancelLike(Long postId, String email) {
        Post post = findPost(postId);
        User user = userService.find(email);

        if (!likeRepository.existsByPostAndUser(post, user)) {
            throw new NotLikedException();
        }

        likeRepository.deleteByPostAndUser(post, user);
        updateLikeCount(post);

        return "좋아요가 삭제되었습니다. 현재 좋아요 수: " + post.getLikesCount();
    }

    // 좋아요 수 갱신
    @Transactional
    public void updateLikeCount(Post post) {
        long actualLikeCount = likeRepository.countByPost(post);
        post.updateLikeCount(actualLikeCount);
        postRepository.save(post);
    }

    public Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    public PostUserResponse findUser(String email) {
        User user = userService.find(email);
        return PostUserResponse.from(user);
    }

    @Transactional
    public PostResponseDto createPostImage(PostRequestDTO postRequestDTO, List<MultipartFile> files, String email) throws IOException {
        // 이메일로 사용자 조회
        User user = userService.find(email); // JWT에서 추출한 이메일 사용

        Post post = Post.create(user, postRequestDTO.content()); // 글 생성 로직 캡슐화
        Post savedPost = postRepository.save(post);

        if (files != null) {
            List<Image> images = imageService.save(files, savedPost);
            post.addImage(images);
        }

        // 저장된 게시글을 ResponseDto에 담아 반환
        return PostResponseDto.from(savedPost);
    }
}
