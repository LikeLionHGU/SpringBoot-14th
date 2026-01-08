package com.thc.back14th.dto.post;

import com.thc.back14th.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;


    private Long authorId;


    // 엔티티를 dto로 변환
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getId()
        );
    }
}
