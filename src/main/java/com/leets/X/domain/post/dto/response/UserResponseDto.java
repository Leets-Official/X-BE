package com.leets.X.domain.post.dto.response;

import com.leets.X.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String userName;

    public static UserResponseDto from(User user) {
        return new UserResponseDto(user.getId(), user.getName());
    }
}
