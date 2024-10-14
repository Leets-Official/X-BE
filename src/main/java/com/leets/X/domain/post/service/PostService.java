package com.leets.X.domain.post.service;

import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.dto.request.PostRequestDTO;
import com.leets.X.domain.post.dto.response.PostResponseDTO;
import com.leets.X.domain.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // Post 저장 로직: DTO를 사용하여 Post 생성 및 저장
    public Post save(PostRequestDTO postRequestDTO) {
        Post post = Post.builder()
                .content(postRequestDTO.getContent())
                .views(0) // 기본 값 설정
                .isDeleted(false) // 기본 값 설정
                .build();
        return postRepository.save(post);
    }


    // 모든 Post 가져오기
        public List<PostResponseDTO> getAllPosts() {
            List<Post> posts = postRepository.findAll();
            return posts.stream()
                    .map(post -> new PostResponseDTO(
                            post.getId(),
                            post.getContent(),
                            post.getViews(),
                            post.getIsDeleted(),
                            post.getLikesCount(),
                            post.getCreatedAt()
                    ))
                    .collect(Collectors.toList());
    }


    // ID로 Post 찾기
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    // Post 삭제
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    // Post 업데이트: 업데이트할 필드가 Map으로 전달됨
    public Post updatePost(Long id, Map<String, Object> updates) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();

            // 업데이트 로직: content 필드를 업데이트할 수 있음
            Post updatedPost = Post.builder()
                    .id(existingPost.getId())
                    .content((String) updates.getOrDefault("content", existingPost.getContent()))
                    .views(existingPost.getViews())
                    .isDeleted(existingPost.getIsDeleted())
                    .deletedAt(existingPost.getDeletedAt())
                    .build();

            return postRepository.save(updatedPost);
        } else {
            throw new RuntimeException("Post not found");
        }
    }
}
