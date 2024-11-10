package com.leets.X.domain.post.service;

import com.leets.X.domain.follow.domain.Follow;
import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.domain.Repost;
import com.leets.X.domain.post.domain.enums.Type;
import com.leets.X.domain.post.dto.response.PostResponseDto;
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
    private final PostService postService;
    private final UserService userService;

    @Transactional
    public void rePost(Long postId, String email) {
        User user = userService.find(email);
        Post post = postService.findPost(postId);

        check(user.getId(), post.getId());

        Repost repost = repostRepository.save(Repost.of(user, post));
        user.addRepost(repost);
    }

    public List<PostResponseDto> getPosts(String email) {
        User user = userService.find(email);

        // 자신의 게시물과 리포스트 가져오기
        List<PostResponseDto> myPosts = getUserPosts(user, true);
        List<PostResponseDto> myReposts = getUserReposts(user, true);

        // 팔로잉한 사용자들의 게시물과 리포스트 가져오기
        List<PostResponseDto> followingPosts = getFollowingUsersPosts(user);
        List<PostResponseDto> followingReposts = getFollowingUsersReposts(user);

        // 모든 게시물 합치고 정렬
        List<PostResponseDto> allPosts = mergeAndSortPosts(myPosts, myReposts, followingPosts, followingReposts);

        return allPosts;
    }

    public List<PostResponseDto> getUserFeed(Long userId) {
        User user = userService.find(userId);

        // 해당 사용자의 게시물과 리포스트 가져오기
        List<PostResponseDto> userPosts = getUserPosts(user, false);
        List<PostResponseDto> userReposts = getUserReposts(user, false);

        // 모든 게시물 합치고 정렬
        List<PostResponseDto> allPosts = mergeAndSortPosts(userPosts, userReposts);

        return allPosts;
    }

    private void check(Long userId, Long postId){
        if(repostRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new AlreadyRepostException();
        }
    }

    // 사용자 자신의 게시물을 가져오는 메서드
    private List<PostResponseDto> getUserPosts(User user, boolean isOwner) {
        return user.getPosts().stream()
                .map(post -> PostResponseDto.from(post, Type.POST, isOwner))
                .collect(Collectors.toList());
    }

    // 사용자 자신의 리포스트를 가져오는 메서드
    private List<PostResponseDto> getUserReposts(User user, boolean isOwner) {
        return user.getReposts().stream()
                .map(repost -> PostResponseDto.from(repost.getPost(), Type.REPOST, isOwner))
                .collect(Collectors.toList());
    }

    // 팔로잉한 사용자들의 게시물을 가져오는 메서드
    private List<PostResponseDto> getFollowingUsersPosts(User user) {
        return user.getFollowingList().stream()
                .map(Follow::getFollowed)
                .flatMap(followedUser -> followedUser.getPosts().stream())
                .map(post -> PostResponseDto.from(post, Type.POST, false))
                .collect(Collectors.toList());
    }

    // 팔로잉한 사용자들의 리포스트를 가져오는 메서드
    private List<PostResponseDto> getFollowingUsersReposts(User user) {
        return user.getFollowingList().stream()
                .map(Follow::getFollowed)
                .flatMap(followedUser -> followedUser.getReposts().stream())
                .map(repost -> PostResponseDto.from(repost.getPost(), Type.REPOST, false))
                .collect(Collectors.toList());
    }

    // 여러 리스트의 게시물을 합치고 정렬하는 메서드
    private List<PostResponseDto> mergeAndSortPosts(List<PostResponseDto>... postLists) {
        List<PostResponseDto> allPosts = new ArrayList<>();
        for (List<PostResponseDto> posts : postLists) {
            allPosts.addAll(posts);
        }
        return allPosts.stream()
                .sorted(Comparator.comparing(PostResponseDto::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

}
