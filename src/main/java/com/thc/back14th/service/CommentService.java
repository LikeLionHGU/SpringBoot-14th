package com.thc.back14th.service;

import com.thc.back14th.domain.Comment;
import com.thc.back14th.domain.Post;
import com.thc.back14th.domain.User;
import com.thc.back14th.dto.comment.CommentCreateRequest;
import com.thc.back14th.dto.comment.CommentResponse;
import com.thc.back14th.repository.CommentRepository;
import com.thc.back14th.repository.PostRepository;
import com.thc.back14th.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Long create(CommentCreateRequest req) {
        Post post = postRepository.findById(req.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글(Post) 없음. id=" + req.getPostId()));
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("작성자(User) 없음. id=" + req.getUserId()));

        Comment c = Comment.builder()
                .content(req.getContent())
                .post(post)
                .author(user)
                .build();

        // 양방향 동기화까지 보여주고 싶으면:
        // Comment c = Comment.builder().content(req.getContent()).build();
        // c.assignPost(post);
        // c.assignAuthor(user);

        return commentRepository.save(c).getId();
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<CommentResponse> findByPost(Long postId) {
        return commentRepository.findByPostId(postId).stream().map(CommentResponse::from).toList();
    }
}
