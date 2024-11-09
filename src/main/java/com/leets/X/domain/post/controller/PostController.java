package com.leets.X.domain.post.controller;


import com.leets.X.domain.post.dto.request.PostRequestDTO;
import com.leets.X.domain.post.dto.response.ParentPostResponseDto;
import com.leets.X.domain.post.dto.response.PostResponseDto;
import com.leets.X.domain.post.service.PostService;
import com.leets.X.global.common.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "POST")
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시물 상세 조회(자식 게시물 까지 함께 조회됨)
    @GetMapping("/{id}")
    @Operation(summary = "게시물 ID로 상세 조회")
    public ResponseDto<PostResponseDto> getPost(@PathVariable Long id, @AuthenticationPrincipal String email) {
        PostResponseDto postResponseDto = postService.getPostResponse(id, email);
        return ResponseDto.response(ResponseMessage.GET_POST_SUCCESS.getCode(), ResponseMessage.GET_POST_SUCCESS.getMessage(), postResponseDto);
    }

    // 모든 부모게시물 조회
    @GetMapping("/all")
    @Operation(summary = "전체 부모 글 조회")
    public ResponseDto<List<ParentPostResponseDto>> getAllParentPosts(@AuthenticationPrincipal String email) {
        List<ParentPostResponseDto> posts = postService.getAllParentPosts(email);
        return ResponseDto.response(ResponseMessage.GET_ALL_PARENT_POSTS_SUCCESS.getCode(), ResponseMessage.GET_ALL_PARENT_POSTS_SUCCESS.getMessage(), posts);
    }


    @GetMapping("/likes")
    @Operation(summary = "좋아요 수로 정렬한 게시물 조회")
    public ResponseDto<List<PostResponseDto>> getPostsSortedByLikes(@AuthenticationPrincipal String email) {
        List<PostResponseDto> posts = postService.getPostsSortedByLikes(email);
        return ResponseDto.response(ResponseMessage.GET_SORTED_BY_LIKES_SUCCESS.getCode(), ResponseMessage.GET_SORTED_BY_LIKES_SUCCESS.getMessage(), posts);
    }


    @GetMapping("/latest")
    @Operation(summary = "최신 게시물 조회")
    public ResponseDto<List<ParentPostResponseDto>> getLatestPosts(@AuthenticationPrincipal String email) {
        List<ParentPostResponseDto> posts = postService.getLatestParentPosts(email);
        return ResponseDto.response(ResponseMessage.GET_LATEST_POST_SUCCESS.getCode(), ResponseMessage.GET_LATEST_POST_SUCCESS.getMessage(), posts);
    }


    @PostMapping("/post")
    @Operation(summary = "글 생성")
    public ResponseDto<PostResponseDto> createPost(@RequestBody PostRequestDTO postRequestDTO, @AuthenticationPrincipal String email) {
        // 인증된 사용자의 이메일을 `@AuthenticationPrincipal`을 통해 주입받음
        PostResponseDto postResponseDto = postService.createPost(postRequestDTO, email);
        return ResponseDto.response(ResponseMessage.POST_SUCCESS.getCode(), ResponseMessage.POST_SUCCESS.getMessage(), postResponseDto);
    }

    @PostMapping("/{postId}/like")
    @Operation(summary = "게시물에 좋아요 추가")
    public ResponseDto<String> addLike(@PathVariable Long postId, @AuthenticationPrincipal String email) {
        String responseMessage = postService.addLike(postId, email);
        return ResponseDto.response(ResponseMessage.ADD_LIKE_SUCCESS.getCode(), responseMessage);
    }

    @PostMapping("/{postId}/reply")
    @Operation(summary = "답글 생성")
    public ResponseDto<PostResponseDto> createReply(@PathVariable Long postId, @RequestBody PostRequestDTO postRequestDTO, @AuthenticationPrincipal String email) {
        // 답글 생성 서비스 호출 (부모 ID를 직접 전달)
        PostResponseDto postResponseDto = postService.createReply(postId, postRequestDTO, email);
        return ResponseDto.response(ResponseMessage.REPLY_SUCCESS.getCode(), ResponseMessage.REPLY_SUCCESS.getMessage(), postResponseDto);
    }


    @DeleteMapping("/{postId}")
    @Operation(summary = "게시물 삭제")
    public ResponseDto<String> deletePost(@PathVariable Long postId, @AuthenticationPrincipal String email) {
        postService.deletePost(postId, email);
        return ResponseDto.response(ResponseMessage.POST_DELETED_SUCCESS.getCode(), ResponseMessage.POST_DELETED_SUCCESS.getMessage());
    }


    @DeleteMapping("/{postId}/like")
    @Operation(summary = "좋아요 취소")
    public ResponseDto<String> cancelLike(@PathVariable Long postId, @AuthenticationPrincipal String email) {
        String responseMessage = postService.cancelLike(postId, email);
        return ResponseDto.response(ResponseMessage.LIKE_CANCEL_SUCCESS.getCode(), responseMessage);
    }



}
