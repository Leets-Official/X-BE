package com.leets.X.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserUpdateRequest(
        @NotBlank String name,
        @NotBlank String introduce,
        @NotBlank String location,
        @NotBlank String webSite,
        @NotNull LocalDate birth
) {
}
