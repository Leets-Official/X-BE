package com.leets.X.domain.image.dto.request;

public record ImageSaveRequest(
        String name,
        String url
) {
    public static ImageSaveRequest of(String name, String url, Integer order) {
        return new ImageSaveRequest(name, url);
    }
}

