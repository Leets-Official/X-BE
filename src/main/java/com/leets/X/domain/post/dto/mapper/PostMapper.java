package com.leets.X.domain.post.dto.mapper;

import com.leets.X.domain.image.dto.response.ImageResponse;
import com.leets.X.domain.like.repository.LikeRepository;
import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.domain.enums.Type;
import com.leets.X.domain.post.dto.response.ParentPostResponseDto;
import com.leets.X.domain.post.dto.response.PostResponseDto;
import com.leets.X.domain.post.dto.response.PostUserResponse;
import com.leets.X.domain.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    public PostResponseDto toPostResponseDto(Post post, User user, LikeRepository likeRepository, Type postType) {
        return PostResponseDto.builder()
                .id(post.getId())
                .content(post.getContent())
                .views(post.getViews())
                .isDeleted(post.getIsDeleted())
                .createdAt(post.getCreatedAt())
                .user(toPostUserResponse(post.getUser()))
                .likeCount(post.getLikeCount())
                .isLikedByUser(isLikedByUser(post, user, likeRepository))
                .postType(postType)
                .myPost(isMyPost(post, user))
//                .replyTo(getCustomId(post))
                .images(toImageResponse(post))
                .replies(toReplies(post.getReplies(), user, likeRepository, postType))
                .build();
    }

    public ParentPostResponseDto toParentPostResponseDto(Post post, User user, LikeRepository likeRepository, Type postType, Long repostingUserId) {
        return ParentPostResponseDto.builder()
                .id(post.getId())
                .content(post.getContent())
                .views(post.getViews())
                .isDeleted(post.getIsDeleted())
                .createdAt(post.getCreatedAt())
                .user(toPostUserResponse(post.getUser()))
                .likeCount(post.getLikeCount())
                .isLikedByUser(isLikedByUser(post, user, likeRepository))
                .repostingUserId(repostingUserId)
                .postType(postType)
                .myPost(isMyPost(post, user))
//                .replyTo(getCustomId(post))
                .images(toImageResponse(post))
                .build();
    }

    public PostUserResponse toPostUserResponse(User user) {
        return PostUserResponse.from(user);
    }

    public List<ImageResponse> toImageResponse(Post post) {
        return post.getImages().stream()
                .map(ImageResponse::from)
                .toList();
    }

    private List<PostResponseDto> toReplies(List<Post> replies, User user, LikeRepository likeRepository, Type postType) {
        return replies.stream()
                .map(reply -> toPostResponseDto(reply, user, likeRepository, postType))
                .collect(Collectors.toList());

    }

    private boolean isLikedByUser(Post post, User user, LikeRepository likeRepository) {
        return likeRepository.existsByPostAndUser(post, user);
    }

    private boolean isMyPost(Post post, User user) {
        return post.getUser().equals(user);
    }

    private String getCustomId(Post post) {
        if(post.getParent() == null){
            return null;
        }
        return post.getParent().getUser().getCustomId();
    }

}
