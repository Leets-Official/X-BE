package com.leets.X.domain.post.controller;

import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.dto.request.PostRequestDTO;
import com.leets.X.domain.post.dto.response.PostResponseDTO;
import com.leets.X.domain.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // POST 요청 처리: Post 생성
    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody PostRequestDTO postRequestDTO) {
        Post savedPost = postService.save(postRequestDTO); // PostRequestDTO를 사용해 Post 저장
        PostResponseDTO responseDTO = new PostResponseDTO(
                savedPost.getId(),
                savedPost.getContent(),
                savedPost.getViews(),
                savedPost.getIsDeleted(),
                savedPost.getLikesCount(),
                savedPost.getCreatedAt()
        );
        return ResponseEntity.ok(responseDTO);
    }

    // GET 요청 처리: 전체 Post 조회
    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        List<PostResponseDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // GET 요청 처리: ID로 Post 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(post -> ResponseEntity.ok(new PostResponseDTO(
                        post.getId(),
                        post.getContent(),
                        post.getViews(),
                        post.getIsDeleted(),
                        post.getLikesCount(),
                        post.getCreatedAt()
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    // PATCH 요청 처리: 특정 Post 수정
    @PatchMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Post updatedPost = postService.updatePost(id, updates);
        PostResponseDTO responseDTO = new PostResponseDTO(
                updatedPost.getId(),
                updatedPost.getContent(),
                updatedPost.getViews(),
                updatedPost.getIsDeleted(),
                updatedPost.getLikesCount(),
                updatedPost.getCreatedAt()
        );
        return ResponseEntity.ok(responseDTO);
    }

    // DELETE 요청 처리: Post 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
