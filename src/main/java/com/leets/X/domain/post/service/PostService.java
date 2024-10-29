package com.leets.X.domain.post.service;

import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.dto.request.PostRequestDTO;
import com.leets.X.domain.post.dto.response.PostResponseDto;
import com.leets.X.domain.post.dto.response.PostResponseDto;
import com.leets.X.domain.post.repository.PostRepository;
import com.leets.X.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

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


}
