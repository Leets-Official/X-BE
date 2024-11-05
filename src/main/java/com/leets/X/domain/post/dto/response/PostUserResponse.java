package com.leets.X.domain.post.dto.response;



import com.leets.X.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserResponse {

    private Long userId;
    private String name;
    private String customId;

    public static PostUserResponse from(User user) {
        return new PostUserResponse(
                user.getId(),
                user.getName(),
                user.getCustomId()
        );

    }
}
