package com.thc.back14th.service;

import com.thc.back14th.domain.Post;
import com.thc.back14th.domain.User;
import com.thc.back14th.dto.post.PostCreateRequest;
import com.thc.back14th.dto.post.PostResponse;
import com.thc.back14th.repository.PostRepository;
import com.thc.back14th.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Long create(PostCreateRequest req) {
        User author = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("작성자(User) 없음. id=" + req.getUserId()));

        Post post = Post.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .author(author) // 단방향처럼 써도 동작
                .build();

        // 양방향 동기화까지 보여주고 싶으면 아래로 교체:
        // Post post = Post.builder().title(...).content(...).build();
        // post.assignAuthor(author);

        return postRepository.save(post).getId();
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PostResponse> findAll() {
        return postRepository.findAll().stream().map(PostResponse::from).toList();
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PostResponse> findByUser(Long userId) {
        return postRepository.findByAuthorId(userId).stream().map(PostResponse::from).toList();
    }
}
