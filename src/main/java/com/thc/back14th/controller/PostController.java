package com.thc.back14th.controller;

import com.thc.back14th.dto.post.PostCreateRequest;
import com.thc.back14th.dto.post.PostResponse;
import com.thc.back14th.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public Long create(@Valid @RequestBody PostCreateRequest req) {
        return postService.create(req);
    }

    @GetMapping
    public List<PostResponse> findAll() {
        return postService.findAll();
    }

    @GetMapping("/by-user/{userId}")
    public List<PostResponse> findByUser(@PathVariable Long userId) {
        return postService.findByUser(userId);
    }
}
