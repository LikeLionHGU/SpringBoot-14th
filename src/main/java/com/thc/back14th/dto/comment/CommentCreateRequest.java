package com.thc.back14th.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentCreateRequest {

    @NotBlank
    private String content;

    private Long postId;
    private Long userId;
}
