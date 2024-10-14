package com.leets.X.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostRequestDTO {

    @NotBlank(message = "Content cannot be empty")
    private String content;



}
