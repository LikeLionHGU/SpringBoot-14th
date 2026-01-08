package com.thc.back14th.controller;

import com.thc.back14th.dto.comment.CommentCreateRequest;
import com.thc.back14th.dto.comment.CommentResponse;
import com.thc.back14th.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public Long create(@Valid @RequestBody CommentCreateRequest req) {
        return commentService.create(req);
    }

    @GetMapping("/by-post/{postId}")
    public List<CommentResponse> findByPost(@PathVariable Long postId) {
        return commentService.findByPost(postId);
    }
}
