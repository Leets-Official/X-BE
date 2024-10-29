package com.leets.X.domain.post.dto.response;

import com.leets.X.domain.image.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponseDto {

    private Long imageId;
    private String url;

    public static ImageResponseDto from(Image image) {
        return new ImageResponseDto(image.getId(), image.getUrl());
    }
}
