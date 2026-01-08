package com.thc.back14th.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "posts")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대형객체를 저장하기 위해서 씀. 긴 본문을 위해 사용.
    private String content;

    //  N : 1 (FK는 여기 posts.user_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;
    /*
    @ManyToOne: Post(N) 여러 개가 User(1) 한 명을 바라본다. 한명의 유저가 여러 포스트를 쓸 수 있다.
    @JoinColumn(name="user_id"): posts 테이블에 user_id 컬럼(FK) 만든다
    nullable=false: posts.user_id NOT NULL → “작성자 없는 글은 존재 불가”
    LAZY: Post 조회할 때 author(User)는 바로 안 가져오고, 필요할 때 가져옴
     */

    //  Post 1 : N Comment (양방향)
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
    /*
    mappedBy="post": “주인은 Comment.post다."
    즉, FK는 comments.post_id 에 있음
     */

    // 연관관계 편의 메서드(양방향 동기화용)
    public void assignAuthor(User user) {
        this.author = user; // 주인(Post.author) 세팅
        user.getPosts().add(this); // 반대편(User.post)에도 넣어줘서 객체가 일관되게 유지됨.
    }
    /*
    왜 필요할까? 양방향에서는 한쪽만 바꾸면 메모리 상태가 꼬인다.
    예: post.author = user만 하면
    user.posts에는 이 post가 없어서 “유저가 쓴 글 목록”이 틀림
     */
}
