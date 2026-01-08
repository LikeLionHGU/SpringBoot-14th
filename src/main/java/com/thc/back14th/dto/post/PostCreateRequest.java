package com.thc.back14th.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostCreateRequest {

    @NotBlank
    private String title;

    private String content;

    // 작성자(User)의 id
    private Long userId;
}
