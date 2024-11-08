package com.leets.X.domain.post.service;

import com.leets.X.domain.like.domain.Like;
import com.leets.X.domain.like.repository.LikeRepository;
import com.leets.X.domain.post.controller.ResponseMessage;
import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.domain.enums.IsDeleted;
import com.leets.X.domain.post.dto.request.PostRequestDTO;
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

    // 게시물 ID로 조회
    public PostResponseDto getPostResponse(Long id, String email) {
        Post post = findPost(id);
        // 삭제되지 않은 게시물만 조회 가능하게끔 수정
        if (post.getIsDeleted() != IsDeleted.ACTIVE) {
            throw new PostNotFoundException();
        }
        User user = userService.find(email);

        return PostResponseDto.from(post,user,likeRepository);
    }

    // 좋아요 수로 정렬한 게시물 조회 (직접 구현)
    public List<PostResponseDto> getPostsSortedByLikes(String email) {
        User user = userService.find(email); // 현재 사용자 정보 조회

        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .filter(post -> post.getIsDeleted() == IsDeleted.ACTIVE)
                .sorted(Comparator.comparing(Post::getLikesCount).reversed())
                .map(post -> PostResponseDto.from(post, user, likeRepository)) // 좋아요 여부 반영
                .collect(Collectors.toList());
    }

    // 최신 게시물 조회

    public List<PostResponseDto> getLatestPosts(String email) {
        User user = userService.find(email);
        // 최신 10개 게시물 조회
        List<Post> posts = postRepository.findTop10ByOrderByCreatedAtDesc();

        // Post 객체를 PostResponseDto로 변환하여 반환
        return posts.stream()
                .map(post -> PostResponseDto.from(post, user, likeRepository)) // Post 객체를 PostResponseDto로 변환
                .collect(Collectors.toList());
    }


    // 글 생성 (Refactoring)
    @Transactional
    public PostResponseDto createPost(PostRequestDTO postRequestDTO, String email) {
        // 이메일로 사용자 조회
        User user = userService.find(email); // JWT에서 추출한 이메일 사용
        if (user == null) {
            throw new UserNotFoundException();
        }

        Post post = Post.create(user, postRequestDTO.content()); // 글 생성 로직 캡슐화
        Post savedPost = postRepository.save(post);

        // 저장된 게시글을 ResponseDto에 담아 반환
        return PostResponseDto.from(savedPost);
    }

    //좋아요 추가
    @Transactional
    public String addLike(Long postId, String email) {
        Post post = findPost(postId);
        User user = userService.find(email);

        // 좋아요가 이미 있는지 확인
        if (likeRepository.existsByPostAndUser(post, user)) {
            throw new AlreadyLikedException();
        }

        // 새로운 Like 객체 생성 및 저장
        Like newLike = new Like(post, user);
        likeRepository.save(newLike);


        // 좋아요 수 갱신
        updateLikecount(post);

        return "좋아요가 추가되었습니다. 현재 좋아요 수: " + post.getLikesCount();
    }

    // 답글 생성
    @Transactional
    public PostResponseDto createReply(Long parentId, PostRequestDTO postRequestDTO, String email) {
        // 이메일을 통해 사용자 조회
        User user = userService.find(email);

        // 부모 글 조회
        Post parentPost = findPost(parentId);

        // 답글 생성
        Post reply = createPost(postRequestDTO, user, parentPost); // 메서드 호출

        // 답글 저장 후 DTO로 변환하여 반환
        return PostResponseDto.from(postRepository.save(reply));
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

        // 게시물 상태를 삭제 상태로 변경
        post.delete(); // delete 메서드 호출로 상태를 변경
    }

    //좋아요 취소
    @Transactional
    public String cancelLike(Long postId, String email) {
        Post post = findPost(postId);
        User user = userService.find(email);

        // 좋아요 여부 확인 후 삭제
        if (!likeRepository.existsByPostAndUser(post, user)) {
            throw new NotLikedException(); // 예외로 변경
        }

        // Like 객체 삭제
        likeRepository.deleteByPostAndUser(post, user);

        updateLikecount(post);

        return "좋아요가 삭제되었습니다. 현재 좋아요 수: " + post.getLikesCount();
    }

    //엔티티 내 메서드를 통해 좋아요 수 갱신 후 DB 반영
    @Transactional
    public void updateLikecount (Post post) {
        long actualLikeCount = likeRepository.countByPost(post);
        post.updateLikeCount(actualLikeCount);
        postRepository.save(post);
    }

    // 자주 중복되는 코드 메서드로 뽑기
    public Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException());
    }

    // 사용자 정보를 가져와 PostUserResponse를 반환하는 메서드
    public PostUserResponse findUser(String email) {
        User user = userService.find(email);
        return PostUserResponse.from(user); // PostUserResponse로 변환하여 반환
    }

    private Post createPost(PostRequestDTO postRequestDTO, User user, Post parentPost) {
        return Post.builder()
                .user(user)
                .content(postRequestDTO.content()) // DTO에서 내용 가져오기
                .views(0)
                .likeCount(0L)
                .isDeleted(IsDeleted.ACTIVE)
                .parent(parentPost) // 부모 글 설정
                .build();
    }

}
