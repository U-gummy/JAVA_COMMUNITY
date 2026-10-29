package com.study.community.postlike;

public class PostLikeNotFoundException extends RuntimeException {
    public PostLikeNotFoundException(Long postId, Long memberId) {
        super("좋아요를 누르지 않은 게시글입니다. postId=" + postId + ", memberId=" + memberId);
    }
}
