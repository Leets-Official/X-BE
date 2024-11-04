package com.leets.X.domain.user.domain;

import com.leets.X.domain.like.domain.Like;
import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.user.domain.enums.Gender;
import com.leets.X.domain.user.dto.request.UserInitializeRequest;
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

   // private Gender gender;

    private String email;

   // private String phoneNum;

    private LocalDate birth;

    private String location;

    private String bio;

    private String webSite;

    private String introduce;

   // private Image image;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    public void initProfile(UserInitializeRequest dto){
        this.birth = dto.birth();
        this.customId = dto.customId();
    }

}
