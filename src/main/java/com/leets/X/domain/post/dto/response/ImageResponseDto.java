package com.leets.X.domain.post.dto.response;

import com.leets.X.domain.image.domain.Image;


public record ImageResponseDto(
        Long imageId,
        String url
) {
    public static ImageResponseDto from(Image image) {
        return new ImageResponseDto(image.getId(), image.getUrl());
    }
}
