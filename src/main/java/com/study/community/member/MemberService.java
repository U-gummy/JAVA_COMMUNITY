package com.study.community.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MemberResponse create(MemberCreateRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException(request.email());
        }

        Member member = new Member(request.email(), request.nickname());

        Member saved = memberRepository.save(member);
        return MemberResponse.from(saved);
    }

    public MemberResponse detail(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
        return MemberResponse.from(member);
    }
}
