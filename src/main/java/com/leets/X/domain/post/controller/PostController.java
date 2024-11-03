package com.leets.X.domain.post.controller;

import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.dto.request.PostRequestDTO;
import com.leets.X.domain.post.dto.response.PostResponseDto;
import com.leets.X.domain.post.service.PostService;
import com.leets.X.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시물 ID로 조회
    @GetMapping("/{id}")
    public ResponseDto<PostResponseDto> getPost(@PathVariable Long id) {
        return postService.getPostResponse(id);
    }

    // 좋아요 수로 정렬한 게시물 조회
    @GetMapping("/likes")
    public ResponseDto<List<PostResponseDto>> getPostsSortedByLikes() {
        return postService.getPostsSortedByLikes();
    }

    // 최신 게시물 조회
    @GetMapping("/latest")
    public ResponseDto<List<PostResponseDto>> getLatestPosts() {
        return postService.getLatestPosts();
    }

    // 글 생성
    @PostMapping("/post")
    public ResponseDto<PostResponseDto> createPost(@RequestBody PostRequestDTO postRequestDTO, @AuthenticationPrincipal String email) {
        // 인증된 사용자의 이메일을 `@AuthenticationPrincipal`을 통해 주입받음
        return postService.createPost(postRequestDTO, email);
    }

    // 게시물에 좋아요 추가
    @PostMapping("/{postId}/like")
    public ResponseDto<String> addLike(@PathVariable Long postId, @AuthenticationPrincipal String email) {
        return postService.addLike(postId, email);
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseDto<String> deletePost(@PathVariable Long postId, @AuthenticationPrincipal String email) {
        return postService.deletePost(postId, email);
    }

    // 좋아요 취소
    @DeleteMapping("/{postId}/like")
    public ResponseDto<String> cancelLike(@PathVariable Long postId, @AuthenticationPrincipal String email) {
        return postService.cancelLike(postId, email);
    }



}
