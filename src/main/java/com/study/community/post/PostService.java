package com.study.community.post;

import com.study.community.category.Category;
import com.study.community.category.CategoryNotFoundException;
import com.study.community.category.CategoryRepository;
import com.study.community.comment.CommentRepository;
import com.study.community.member.Member;
import com.study.community.member.MemberNotFoundException;
import com.study.community.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;


    public PostService(
            PostRepository postRepository,
            MemberRepository memberRepository,
            CategoryRepository categoryRepository,
            CommentRepository commentRepository

    ) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public PostResponse create(PostCreateRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new MemberNotFoundException(request.memberId()));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.categoryId()));


        Post post = new Post(request.title(), request.content(), member, category);

        Post saved = postRepository.save(post);

        return PostResponse.from(saved);
    }

    public List<PostResponse> list() {
        return postRepository.findAllWithMemberAndCategory().stream()
                .map(PostResponse::from)
                .toList();
    }


    public PostResponse detail(Long id) {
        Post post = postRepository.findByIdWithMemberAndCategory(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        return PostResponse.from(post);
    }

    @Transactional
    public PostResponse update(Long id, PostUpdateRequest request, Long memberId) {

        Post post = postRepository.findByIdWithMemberAndCategory(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        if (!memberId.equals(post.getMember().getId())) {
            throw new PostAccessDeniedException();
        }

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.categoryId()));

        post.update(request.title(), request.content(), category);

        return PostResponse.from(post);
    }

    @Transactional
    public void delete(Long id, Long memberId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        if (!memberId.equals(post.getMember().getId())) {
            throw new PostAccessDeniedException();
        }

        commentRepository.deleteAllByPostId(id);
        postRepository.delete(post);
    }

}
