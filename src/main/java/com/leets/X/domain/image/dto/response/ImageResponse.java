package com.leets.X.domain.image.dto.response;

import com.leets.X.domain.image.domain.Image;

public record ImageResponse(
        Long id,
        String name,
        String url
) {
    public static ImageResponse from(Image image) {
        return new ImageResponse(image.getId(), image.getName(), image.getUrl());
    }
}
