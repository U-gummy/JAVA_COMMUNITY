package com.study.community.postlike;

public record PostLikeResponse(
        Long id,
        Long postId,
        Long memberId,
        String nickname
) {
    public static PostLikeResponse from(PostLike postLike) {
        return new PostLikeResponse(
                postLike.getId(),
                postLike.getPost().getId(),
                postLike.getMember().getId(),
                postLike.getMember().getNickname()
        );
    }
}
