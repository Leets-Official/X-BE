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


}
