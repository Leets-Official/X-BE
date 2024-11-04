package com.leets.X.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PostRequestDTO(@NotBlank(message = "Content cannot be empty") String content) {

    public static PostRequestDTO from(String content) {
        return new PostRequestDTO(content);
    }
}
