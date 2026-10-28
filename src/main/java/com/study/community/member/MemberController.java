package com.study.community.member;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberResponse> create(@Valid @RequestBody MemberCreateRequest request) {
        MemberResponse response = memberService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> detail(@PathVariable Long id) {
        MemberResponse response = memberService.detail(id);
        return ResponseEntity.ok(response);
    }
}
