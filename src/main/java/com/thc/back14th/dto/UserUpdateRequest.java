package com.thc.back14th.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

    @NotBlank(message = "이름은 필수입니다.")
    private String name;
}
