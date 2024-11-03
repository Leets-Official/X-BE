package com.leets.X.domain.post.service;

import com.leets.X.domain.like.domain.Like;
import com.leets.X.domain.like.repository.LikeRepository;
import com.leets.X.domain.post.controller.ResponseMessage;
import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.domain.enums.IsDeleted;
import com.leets.X.domain.post.dto.request.PostRequestDTO;
import com.leets.X.domain.post.dto.response.PostResponseDto;
import com.leets.X.domain.post.exception.PostNotFoundException;
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
    public ResponseDto<PostResponseDto> getPostResponse(Long id) {
        Post post = findPost(id);
        // 삭제되지 않은 게시물만 조회 가능하게끔 수정
        if (post.getIsDeleted() != IsDeleted.ACTIVE) {
            throw new PostNotFoundException();
        }
        PostResponseDto postResponseDto = PostResponseDto.from(post);
        return ResponseDto.response(ResponseMessage.GET_POST_SUCCESS.getCode(), ResponseMessage.GET_POST_SUCCESS.getMessage(), postResponseDto);
    }

    // 좋아요 수로 정렬한 게시물 조회 (직접 구현)
    public ResponseDto<List<PostResponseDto>> getPostsSortedByLikes() {
        List<Post> posts = postRepository.findAll(); // 모든 게시물 조회

        List<PostResponseDto> sortedPosts = posts.stream()
                .filter(post -> post.getIsDeleted() == IsDeleted.ACTIVE) // ACTIVE 상태만 필터링
                .sorted(Comparator.comparing(Post::getLikesCount).reversed()) // 좋아요 수 기준으로 내림차순 정렬
                .map(PostResponseDto::from) // DTO로 변환
                .collect(Collectors.toList());

        return ResponseDto.response(ResponseMessage.GET_SORTED_BY_LIKES_SUCCESS.getCode(), ResponseMessage.GET_SORTED_BY_LIKES_SUCCESS.getMessage(), sortedPosts);
    }

    // 최신 게시물 조회
    public ResponseDto<List<PostResponseDto>> getLatestPosts() {
        List<Post> posts = postRepository.findTop10ByOrderByCreatedAtDesc(); // 최신 10개 게시물 조회
        // 현재 시각 가져오기
        LocalDateTime now = LocalDateTime.now();

        // 게시물을 PostResponseDto로 변환하고 정렬
        List<PostResponseDto> postResponseDtos = posts.stream()
                .map(PostResponseDto::from) // Post 객체를 PostResponseDto로 변환
                .sorted(Comparator.comparingLong(postResponseDto ->
                        Math.abs(postResponseDto.getCreatedAt().until(now, java.time.temporal.ChronoUnit.SECONDS)))) // 현재 시각과의 차이를 기준으로 정렬
                .collect(Collectors.toList());

        return ResponseDto.response(ResponseMessage.GET_LATEST_POST_SUCCESS.getCode(), ResponseMessage.GET_LATEST_POST_SUCCESS.getMessage(), postResponseDtos);
    }

    // 글 생성 (Refactoring)
    @Transactional
    public ResponseDto<PostResponseDto> createPost(PostRequestDTO postRequestDTO, String email) {
        // 이메일로 사용자 조회
        User user = userService.find(email); // JWT에서 추출한 이메일 사용
        if (user == null) {
            throw new UserNotFoundException();
        }

        // Post 생성 후 기본 값 설정
        Post post = Post.builder()
                .user(user)
                .content(postRequestDTO.getContent())
                .views(0)
                .isDeleted(IsDeleted.ACTIVE) // 기본값 ACTIVE로 설정
                .build();
        Post savedPost = postRepository.save(post);

        // 저장된 게시글을 ResponseDto에 담아 반환
        PostResponseDto postResponseDTO = PostResponseDto.from(savedPost);
        return ResponseDto.response(ResponseMessage.POST_SUCCESS.getCode(), ResponseMessage.POST_SUCCESS.getMessage(), postResponseDTO);
    }

    //좋아요 추가
    @Transactional
    public ResponseDto<String> addLike(Long postId, String email) {
        Post post = findPost(postId);
        User user = userService.find(email);

        // 좋아요가 이미 있는지 확인
        if (likeRepository.existsByPostAndUser(post, user)) {
            return ResponseDto.response(400, "이미 좋아요를 누른 게시물입니다.");
        }

        // 새로운 Like 객체 생성 및 저장
        Like newLike = new Like(post, user);
        likeRepository.save(newLike);

        // 현재 좋아요 개수 반환
        long likeCount = likeRepository.countByPost(post);
        return ResponseDto.response(ResponseMessage.ADD_LIKE_SUCCESS.getCode(),
                "좋아요가 추가되었습니다. 현재 좋아요 수: " + likeCount);
    }

    // 게시물 삭제
    @Transactional
    public ResponseDto<String> deletePost(Long postId, String email) {
        Post post = findPost(postId);
        User user = userService.find(email);

        // 게시물의 소유자인지 확인
        if (!post.getUser().equals(user)) {
            return ResponseDto.response(403, "게시물을 삭제할 권한이 없습니다.");
        }

        // 게시물 상태를 삭제 상태로 변경
        post.delete(); // delete 메서드 호출로 상태를 변경
        postRepository.save(post); // 상태 업데이트

        return ResponseDto.response(ResponseMessage.POST_DELETED_SUCCESS.getCode(), ResponseMessage.POST_DELETED_SUCCESS.getMessage());
    }

    @Transactional
    public ResponseDto<String> cancelLike(Long postId, String email) {
        Post post = findPost(postId);
        User user = userService.find(email);

        // 좋아요 여부 확인 후 삭제
        if (!likeRepository.existsByPostAndUser(post, user)) {
            return ResponseDto.response(400, "좋아요가 눌려 있지 않은 게시물입니다.");
        }

        // Like 객체 삭제
        likeRepository.deleteByPostAndUser(post, user);

        // 현재 좋아요 개수 반환
        long likeCount = likeRepository.countByPost(post);
        return ResponseDto.response(ResponseMessage.LIKE_CANCEL_SUCCESS.getCode(),
                "좋아요가 삭제되었습니다. 현재 좋아요 수: " + likeCount);
    }

    // 자주 중복되는 코드 메서드로 뽑기
    public Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException());
    }
}
