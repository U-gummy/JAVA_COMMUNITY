package com.study.community.post;

public record PostResponse(
        Long id, String title, String content,
        Long memberId, String nickname,
        Long categoryId, String categoryName
) {

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMember().getId(),
                post.getMember().getNickname(),
                post.getCategory().getId(),
                post.getCategory().getName()
        );

    }
}
