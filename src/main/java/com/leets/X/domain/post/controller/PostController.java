package com.leets.X.domain.post.controller;


import com.leets.X.domain.post.dto.request.PostRequestDTO;
import com.leets.X.domain.post.dto.response.ParentPostResponseDto;
import com.leets.X.domain.post.dto.response.PostResponseDto;
import com.leets.X.domain.post.service.PostService;
import com.leets.X.domain.post.service.RepostService;
import com.leets.X.global.common.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


//컨트롤러에서 ResponseDto만들게끔
@Tag(name = "POST")
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final RepostService repostService;

    // 게시물 상세 조회(자식 게시물 까지 함께 조회됨)
    @GetMapping("/{id}")
    @Operation(summary = "게시물 ID로 상세 조회")
    public ResponseDto<PostResponseDto> getPost(@PathVariable Long id, @AuthenticationPrincipal String email) {
        PostResponseDto postResponseDto = postService.getPostResponse(id, email);
        return ResponseDto.response(ResponseMessage.GET_POST_SUCCESS.getCode(), ResponseMessage.GET_POST_SUCCESS.getMessage(), postResponseDto);
    }

    // 모든 부모게시물 조회
    @GetMapping("/all")
    @Operation(summary = "[Home] 추천 게시글")
    public ResponseDto<List<ParentPostResponseDto>> getAllParentPosts(@AuthenticationPrincipal String email) {
        List<ParentPostResponseDto> posts = postService.getAllParentPosts(email);
        return ResponseDto.response(ResponseMessage.GET_ALL_PARENT_POSTS_SUCCESS.getCode(), ResponseMessage.GET_ALL_PARENT_POSTS_SUCCESS.getMessage(), posts);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "[Profile] 유저 게시글 조회")
    public ResponseDto<List<ParentPostResponseDto>> getAllUserPosts(@PathVariable Long userId) {
        List<ParentPostResponseDto> posts = repostService.getUserFeed(userId);
        return ResponseDto.response(ResponseMessage.GET_USER_POST.getCode(), ResponseMessage.GET_USER_POST.getMessage(), posts);
    }

    @GetMapping("/following")
    @Operation(summary = "[Home] 팔로잉 게시글 조회")
    public ResponseDto<List<ParentPostResponseDto>> getAllFollowingPosts(@AuthenticationPrincipal String email) {
        List<ParentPostResponseDto> posts = repostService.getFollowingPost(email);
        return ResponseDto.response(ResponseMessage.GET_FOLLOWING_POST.getCode(), ResponseMessage.GET_FOLLOWING_POST.getMessage(), posts);
    }


//    @GetMapping("/likes")
//    @Operation(summary = "좋아요 수로 정렬한 게시물 조회")
//    public ResponseDto<List<PostResponseDto>> getPostsSortedByLikes(@AuthenticationPrincipal String email) {
//        List<PostResponseDto> posts = postService.getPostsSortedByLikes(email);
//        return ResponseDto.response(ResponseMessage.GET_SORTED_BY_LIKES_SUCCESS.getCode(), ResponseMessage.GET_SORTED_BY_LIKES_SUCCESS.getMessage(), posts);
//    }


//    @GetMapping("/latest")
//    @Operation(summary = "최신 게시물 조회")
//    public ResponseDto<List<ParentPostResponseDto>> getLatestPosts(@AuthenticationPrincipal String email) {
//        List<ParentPostResponseDto> posts = postService.getLatestParentPosts(email);
//        return ResponseDto.response(ResponseMessage.GET_LATEST_POST_SUCCESS.getCode(), ResponseMessage.GET_LATEST_POST_SUCCESS.getMessage(), posts);
//    }


    @PostMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "글 생성")
    public ResponseDto<String> createPost(@RequestPart PostRequestDTO postRequestDTO,
                                                   @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                                   @AuthenticationPrincipal String email) throws IOException {
        // 인증된 사용자의 이메일을 `@AuthenticationPrincipal`을 통해 주입받음
        postService.createPost(postRequestDTO, files , email);
        return ResponseDto.response(ResponseMessage.POST_SUCCESS.getCode(), ResponseMessage.POST_SUCCESS.getMessage());
    }

    @PostMapping("/{postId}/like")
    @Operation(summary = "게시물에 좋아요 추가")
    public ResponseDto<String> addLike(@PathVariable Long postId, @AuthenticationPrincipal String email) {
        String responseMessage = postService.addLike(postId, email);
        return ResponseDto.response(ResponseMessage.ADD_LIKE_SUCCESS.getCode(), responseMessage);
    }

    @PostMapping(value = "/{postId}/reply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "답글 생성")
    public ResponseDto<String> createReply(@PathVariable Long postId,
                                                    @RequestPart PostRequestDTO postRequestDTO,
                                                    @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                                    @AuthenticationPrincipal String email) throws IOException {
        // 답글 생성 서비스 호출 (부모 ID를 직접 전달)
        postService.createReply(postId, postRequestDTO, files, email);
        return ResponseDto.response(ResponseMessage.REPLY_SUCCESS.getCode(), ResponseMessage.REPLY_SUCCESS.getMessage());
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

    @PostMapping("/repost/{postId}")
    @Operation(summary = "Repost 하기")
    public ResponseDto<String> repost(@PathVariable Long postId, @AuthenticationPrincipal String email) {
        repostService.rePost(postId, email);
        return ResponseDto.response(ResponseMessage.REPOST_SUCCESS.getCode(), ResponseMessage.REPOST_SUCCESS.getMessage());
    }

}
