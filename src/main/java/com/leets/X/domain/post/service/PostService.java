package com.leets.X.domain.post.service;

import com.leets.X.domain.like.domain.Like;
import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.domain.enums.IsDeleted;
import com.leets.X.domain.post.dto.request.PostRequestDTO;
import com.leets.X.domain.post.dto.response.PostResponseDto;
import com.leets.X.domain.post.repository.PostRepository;
import com.leets.X.domain.user.domain.User;
import com.leets.X.domain.user.repository.UserRepository;
import com.leets.X.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 게시물 ID로 조회
    public ResponseDto<PostResponseDto> getPostResponse(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시물 입니다"));
        PostResponseDto postResponseDto = PostResponseDto.from(post);
        return ResponseDto.response(200, "게시물이 성공적으로 조회되었습니다.", postResponseDto);
    }

    //좋아요 수로 정렬한 게시물 조회 (직접 구현)
    public ResponseDto<List<PostResponseDto>> getPostsSortedByLikes() {
        List<Post> posts = postRepository.findAll(); // 모든 게시물 조회
        // 좋아요 수로 정렬
        List<Post> sortedPosts = posts.stream()
                .sorted(Comparator.comparing(Post::getLikesCount).reversed()) // 좋아요 수 기준으로 내림차순 정렬
                .collect(Collectors.toList());
        // DTO로 변환
        List<PostResponseDto> postResponseDtos = sortedPosts.stream()
                .map(PostResponseDto::from)
                .collect(Collectors.toList());
        return ResponseDto.response(200, "게시물이 좋아요 수로 정렬되었습니다.", postResponseDtos);
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

        return ResponseDto.response(200, "최신게시물이 조회되었습니다.", postResponseDtos);
    }

    //글 생성
    public ResponseDto<PostResponseDto> createPost(PostRequestDTO postRequestDTO, Long userId) {
        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
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
        return ResponseDto.response(200, "게시물이 성공적으로 생성되었습니다.", postResponseDTO);
    }


    // 좋아요 추가
    @Transactional
    public ResponseDto<String> addLike(Long postId, Long userId) {
        // 사용자 & 게시물 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시물입니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        // 이미 좋아요가 눌려 있는지 확인
        boolean alreadyLiked = post.getLikes().stream()
                .anyMatch(like -> like.getUser().equals(user));
        if (alreadyLiked) {
            return ResponseDto.response(400, "이미 좋아요를 누른 게시물입니다.");
        }

        // 새로운 좋아요 추가
        Like newLike = new Like(post, user);  // Like 엔티티의 생성자 사용
        post.getLikes().add(newLike);
        postRepository.save(post);

        return ResponseDto.response(200, "게시물에 좋아요가 추가되었습니다.");
    }
}