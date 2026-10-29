package com.study.community.postlike;

import com.study.community.member.Member;
import com.study.community.member.MemberNotFoundException;
import com.study.community.member.MemberRepository;
import com.study.community.post.Post;
import com.study.community.post.PostNotFoundException;
import com.study.community.post.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;


    public PostLikeService(
            PostLikeRepository postLikeRepository,
            PostRepository postRepository,
            MemberRepository memberRepository
    ) {
        this.postLikeRepository = postLikeRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public PostLikeResponse create(Long postId, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        if (postLikeRepository.existsByPostIdAndMemberId(postId, memberId)) {
            throw new DuplicatePostLikeException(postId, memberId);
        }

        PostLike postLike = new PostLike(post, member);
        PostLike saved = postLikeRepository.save(postLike);

        return PostLikeResponse.from(saved);
    }

    @Transactional
    public void delete(Long postId, Long memberId) {
        PostLike postLike = postLikeRepository.findByPostIdAndMemberId(postId, memberId)
                .orElseThrow(() -> new PostLikeNotFoundException(postId, memberId));

        postLikeRepository.delete(postLike);
    }
}
