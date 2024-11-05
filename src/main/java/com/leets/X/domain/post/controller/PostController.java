package com.leets.X.domain.post.controller;

import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.dto.request.PostRequestDTO;
import com.leets.X.domain.post.dto.response.PostResponseDto;
import com.leets.X.domain.post.service.PostService;
import com.leets.X.global.common.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@Tag(name = "POST")
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @GetMapping("/{id}")
    @Operation(summary = "게시물 ID로 조회")
    public ResponseDto<PostResponseDto> getPost(@PathVariable Long id) {
        return postService.getPostResponse(id);
    }


    @GetMapping("/likes")
    @Operation(summary = "좋아요 수로 정렬한 게시물 조회")
    public ResponseDto<List<PostResponseDto>> getPostsSortedByLikes() {
        return postService.getPostsSortedByLikes();
    }

    
    @GetMapping("/latest")
    @Operation(summary = "최신 게시물 조회")
    public ResponseDto<List<PostResponseDto>> getLatestPosts() {
        return postService.getLatestPosts();
    }


    @PostMapping("/post")
    @Operation(summary = "글 생성")
    public ResponseDto<PostResponseDto> createPost(@RequestBody PostRequestDTO postRequestDTO, @AuthenticationPrincipal String email) {
        // 인증된 사용자의 이메일을 `@AuthenticationPrincipal`을 통해 주입받음
        return postService.createPost(postRequestDTO, email);
    }


    @PostMapping("/{postId}/like")
    @Operation(summary = "게시물에 좋아요 추가")
    public ResponseDto<String> addLike(@PathVariable Long postId, @AuthenticationPrincipal String email) {
        return postService.addLike(postId, email);
    }


    @DeleteMapping("/{postId}")
    @Operation(summary = "게시물 삭제")
    public ResponseDto<String> deletePost(@PathVariable Long postId, @AuthenticationPrincipal String email) {
        return postService.deletePost(postId, email);
    }


    @DeleteMapping("/{postId}/like")
    @Operation(summary = "좋아요 취소")
    public ResponseDto<String> cancelLike(@PathVariable Long postId, @AuthenticationPrincipal String email) {
        return postService.cancelLike(postId, email);
    }



}
