package com.study.community.postlike;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
public class PostLikeController {
    private final PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @PostMapping
    public ResponseEntity<PostLikeResponse> create(@PathVariable Long postId, @RequestParam Long memberId) {
        PostLikeResponse response = postLikeService.create(postId, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long postId, @RequestParam Long memberId) {
        postLikeService.delete(postId, memberId);
        return ResponseEntity.noContent().build();
    }
}
