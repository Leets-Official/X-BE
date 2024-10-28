package com.leets.X.domain.post.service;

import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.dto.request.PostRequestDTO;
import com.leets.X.domain.post.dto.response.PostResponseDTO;
import com.leets.X.domain.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public PostResponseDTO createPost(PostRequestDTO postRequestDTO) {
        Post post = Post.builder()
                .content(postRequestDTO.getContent())
                .views(0)
                .isDeleted(false)
                .build();
        Post savedPost = postRepository.save(post);
        return toResponseDTO(savedPost);
    }

    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<PostResponseDTO> getPostById(Long id) {
        return postRepository.findById(id).map(this::toResponseDTO);
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional
    public PostResponseDTO updatePost(Long id, Map<String, Object> updates) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        if (updates.containsKey("content")) {
            post.setContent((String) updates.get("content"));
        }

        Post updatedPost = postRepository.save(post);
        return toResponseDTO(updatedPost);
    }

    private PostResponseDTO toResponseDTO(Post post) {
        return new PostResponseDTO(
                post.getId(),
                post.getContent(),
                post.getViews(),
                post.getIsDeleted(),
                post.getLikesCount(),
                post.getCreatedAt()
        );
    }
}
