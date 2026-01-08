package com.thc.back14th.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "comments")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String content;

    //  N : 1 (comments.post_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    //  N : 1 (comments.user_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    /*
    post_id : “이 댓글이 달린 게시글”
    user_id : “이 댓글을 쓴 유저”
     */

    /*public void assignPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    public void assignAuthor(User user) {
        this.author = user;
        user.getComments().add(this);
    }
    양방향 동기화인데, 안해도 괜찮을듯.
     */
}
