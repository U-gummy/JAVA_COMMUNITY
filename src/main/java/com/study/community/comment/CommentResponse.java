package com.study.community.comment;

public record CommentResponse(
        Long id,
        String content,
        Long postId,
        Long memberId,
        String nickname
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getPost().getId(),
                comment.getMember().getId(),
                comment.getMember().getNickname()
        );
    }
}
