package com.leets.X.domain.user.domain;

import com.leets.X.domain.follow.domain.Follow;
import com.leets.X.domain.user.dto.request.UserInitializeRequest;
import com.leets.X.domain.user.dto.request.UserUpdateRequest;
import com.leets.X.global.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
// mysql에서 user 테이블이 존재 하기 때문에 다른 이름으로 지정
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    //private String password; // 소셜로그인 구현에는 필요 x

    private String customId;

    private String name;

//    private Gender gender;

    private String email;

//    private String phoneNum;

    private LocalDate birth;

    private String location;

    private String bio;

    private String webSite;

    private String introduce;

//    private Image image;

    @OneToMany(mappedBy = "followed", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Follow> followerList = new ArrayList<>();

    @OneToMany(mappedBy = "follower", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Follow> followingList = new ArrayList<>();

    public void initProfile(UserInitializeRequest dto){
        this.birth = dto.birth();
        this.customId = dto.customId();
    }

    public void update(UserUpdateRequest dto){
        this.name = dto.name();
        this.introduce = dto.introduce();
        this.location = dto.location();
        this.webSite = dto.webSite();
    }

    public void addFollower(Follow follow) {
        this.followerList.add(follow);
    }

    public void addFollowing(Follow follow) {
        this.followingList.add(follow);
    }

    public void removeFollower(Follow follow) {
        this.followerList.remove(follow);
    }

    public void removeFollowing(Follow follow) {
        this.followingList.remove(follow);
    }



}
