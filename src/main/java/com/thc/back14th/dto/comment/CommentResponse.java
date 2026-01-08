package com.thc.back14th.dto.comment;

import com.thc.back14th.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String content;
    private Long postId;
    private Long authorId;

    // 엔티티를 dto로 변환
    public static CommentResponse from(Comment c) {
        return new CommentResponse(
                c.getId(),
                c.getContent(),
                c.getPost().getId(),
                c.getAuthor().getId()
        );
    }
}
