package com.study.community.postlike;

public class DuplicatePostLikeException extends RuntimeException {
    public DuplicatePostLikeException(Long postId, Long memberId) {
        super("이미 좋아요를 누른 게시글입니다. postId=" + postId + ", memberId=" + memberId);
    }
}
