package com.leets.X.domain.post.controller;

import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.dto.request.PostRequestDTO;
import com.leets.X.domain.post.dto.response.PostResponseDto;
import com.leets.X.domain.post.service.PostService;
import com.leets.X.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시물 ID로 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<PostResponseDto>> getPost(@PathVariable Long id) {
        ResponseDto<PostResponseDto> response = postService.getPostResponse(id);
        return ResponseEntity.ok(response);
    }

    // 좋아요 수로 정렬한 게시물 조회
    @GetMapping("/likes")
    public ResponseEntity<ResponseDto<List<PostResponseDto>>> getPostsSortedByLikes() {
        ResponseDto<List<PostResponseDto>> response = postService.getPostsSortedByLikes();
        return ResponseEntity.ok(response);
    }

    // 최신 게시물 조회
    @GetMapping("/latest")
    public ResponseEntity<ResponseDto<List<PostResponseDto>>> getLatestPosts() {
        ResponseDto<List<PostResponseDto>> response = postService.getLatestPosts();
        return ResponseEntity.ok(response);
    }

    // 글 생성
    @PostMapping("/post")
    public ResponseEntity<ResponseDto<PostResponseDto>> createPost(@RequestBody PostRequestDTO postRequestDTO, @RequestParam Long userId) {

        ResponseDto<PostResponseDto> response = postService.createPost(postRequestDTO, userId);
        return ResponseEntity.ok(response);
    }

    // 게시물에 좋아요 추가
    @PostMapping("/{postId}/like")
    public ResponseEntity<ResponseDto<String>> addLike(@PathVariable Long postId, @RequestParam Long userId) {

        ResponseDto<String> response = postService.addLike(postId, userId);
        return ResponseEntity.ok(response);
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseDto<String>> deletePost(@PathVariable Long postId, @RequestParam Long userId) {
        ResponseDto<String> response = postService.deletePost(postId, userId);
        return ResponseEntity.ok(response);
    }

    // 좋아요 취소
    @DeleteMapping("/{postId}/like")
    public ResponseEntity<ResponseDto<String>> cancelLike(@PathVariable Long postId, @RequestParam Long userId) {
        ResponseDto<String> response = postService.cancelLike(postId, userId);
        return ResponseEntity.ok(response);
    }


}
