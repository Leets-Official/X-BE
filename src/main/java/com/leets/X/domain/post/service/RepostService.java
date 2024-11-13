package com.leets.X.domain.post.service;

import com.leets.X.domain.follow.domain.Follow;
import com.leets.X.domain.like.repository.LikeRepository;
import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.domain.Repost;
import com.leets.X.domain.post.domain.enums.Type;
import com.leets.X.domain.post.dto.mapper.PostMapper;
import com.leets.X.domain.post.dto.response.ParentPostResponseDto;
import com.leets.X.domain.post.exception.AlreadyRepostException;
import com.leets.X.domain.post.repository.PostRepository;
import com.leets.X.domain.post.repository.RepostRepository;
import com.leets.X.domain.user.domain.User;
import com.leets.X.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepostService {
    private final RepostRepository repostRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final PostService postService;
    private final UserService userService;
    private final PostMapper postMapper;

    @Transactional
    public void rePost(Long postId, String email) {
        User user = userService.find(email);
        Post post = postService.findPost(postId);

        Repost repost = repostRepository.save(Repost.of(user, post));
        user.addRepost(repost);
        post.increaseReplyCount();
    }

    public List<ParentPostResponseDto> getFollowingPost(String email) {
        User user = userService.find(email);

        // 자신의 게시물과 리포스트 가져오기
        List<ParentPostResponseDto> myPosts = getUserPosts(user, true);
        List<ParentPostResponseDto> myReposts = getUserReposts(user, true);

        // 팔로잉한 사용자들의 게시물과 리포스트 가져오기
        List<ParentPostResponseDto> followingPosts = getFollowingUsersPosts(user);
        List<ParentPostResponseDto> followingReposts = getFollowingUsersReposts(user);

        // 모든 게시물 합치고 정렬
        List<ParentPostResponseDto> allPosts = mergeAndSortPosts(myPosts, myReposts, followingPosts, followingReposts);

        return allPosts;
    }

    public List<ParentPostResponseDto> getUserFeed(Long userId) {
        User user = userService.find(userId);

        // 해당 사용자의 게시물과 리포스트 가져오기
        List<ParentPostResponseDto> userPosts = getUserPosts(user, false);
        List<ParentPostResponseDto> userReposts = getUserReposts(user, false);

        // 모든 게시물 합치고 정렬
        List<ParentPostResponseDto> allPosts = mergeAndSortPosts(userPosts, userReposts);

        return allPosts;
    }

    // 내 글을 리포스트 하는 것도 가능하기에 제거
    private void check(Long userId, Long postId){
        if(repostRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new AlreadyRepostException();
        }
    }

    // 사용자의 게시물을 가져오는 메서드
    private List<ParentPostResponseDto> getUserPosts(User user, boolean isOwner) {
        return user.getPosts().stream()
                .filter(post -> post.getParent() == null)
                .map(post -> postMapper.toParentPostResponseDto(post, user, likeRepository, Type.POST, null, null))
                .collect(Collectors.toList());
    }

    // 사용자의 리포스트를 가져오는 메서드
    private List<ParentPostResponseDto> getUserReposts(User user, boolean isOwner) {
        return user.getReposts().stream()
                .map(repost -> postMapper.toParentPostResponseDto(repost.getPost(), user, likeRepository, Type.REPOST, repost.getUser().getId(), repost.getUser().getName()))
                .collect(Collectors.toList());
    }

    // 팔로잉한 사용자들의 게시물을 가져오는 메서드
    private List<ParentPostResponseDto> getFollowingUsersPosts(User user) {
        return user.getFollowingList().stream()
                .map(Follow::getFollowed)
                .flatMap(followedUser -> followedUser.getPosts().stream())
                .filter(post -> post.getParent() == null)
                .map(post -> postMapper.toParentPostResponseDto(post, user, likeRepository, Type.POST, null, null))
                .collect(Collectors.toList());
    }

    // 팔로잉한 사용자들의 리포스트를 가져오는 메서드
    private List<ParentPostResponseDto> getFollowingUsersReposts(User user) {
        return user.getFollowingList().stream()
                .map(Follow::getFollowed)
                .flatMap(followedUser -> followedUser.getReposts().stream())
                .map(repost -> postMapper.toParentPostResponseDto(repost.getPost(), user, likeRepository, Type.REPOST, repost.getUser().getId(), repost.getUser().getName()))
                .collect(Collectors.toList());
    }

    // 여러 리스트의 게시물을 합치고 정렬하는 메서드
    private List<ParentPostResponseDto> mergeAndSortPosts(List<ParentPostResponseDto>... postLists) {
        List<ParentPostResponseDto> allPosts = new ArrayList<>();
        for (List<ParentPostResponseDto> posts : postLists) {
            allPosts.addAll(posts);
        }
        return allPosts.stream()
                .sorted(Comparator.comparing(ParentPostResponseDto::createdAt).reversed())
                .collect(Collectors.toList());
    }

}
