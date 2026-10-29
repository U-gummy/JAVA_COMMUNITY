package com.study.community.comment;

import com.study.community.member.Member;
import com.study.community.member.MemberNotFoundException;
import com.study.community.member.MemberRepository;
import com.study.community.post.Post;
import com.study.community.post.PostNotFoundException;
import com.study.community.post.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public CommentService(
            CommentRepository commentRepository,
            PostRepository postRepository,
            MemberRepository memberRepository
    ) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public CommentResponse create(Long postId, CommentCreateRequest request, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        Comment comment = new Comment(request.content(), post, member);
        Comment saved = commentRepository.save(comment);

        return CommentResponse.from(saved);
    }

    public List<CommentResponse> list(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        return commentRepository.findAllByPostIdWithMember(postId).stream()
                .map(CommentResponse::from)
                .toList();
    }

    @Transactional
    public void delete(Long id, Long memberId) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        if (!memberId.equals(comment.getMember().getId())) {
            throw new CommentAccessDeniedException();
        }

        commentRepository.delete(comment);
    }

}
