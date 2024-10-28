package com.leets.X.domain.post.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Getter
public class PostRequestDTO {

    @JsonCreator
    public PostRequestDTO(@JsonProperty("content") String content) {
        this.content = content;
    }
    @NotBlank(message = "Content cannot be empty")
    private String content;



}
