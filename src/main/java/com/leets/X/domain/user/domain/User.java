package com.leets.X.domain.user.domain;

import com.leets.X.domain.user.domain.enums.Gender;
import com.leets.X.global.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
// mysql에서 user 테이블이 존재 하기 때문에 다른 이름으로 지정
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String password;

    private String customId;

    private String name;

    private Gender gender;

    private String email;

    private String phoneNum;

    private LocalDateTime birth;

    private String location;

    private String bio;

    private String webSite;

    private String introduce;

//    private Image image;

}
