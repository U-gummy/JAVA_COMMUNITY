package com.study.community.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberCreateRequest(
        @NotBlank(message = "이메일은 필수입니다.")
        @Size(max = 100, message = "이메일은 100자 이하여야 합니다.")
        @Email(message = "이메일 형식이어야 합니다.")
        String email,
        @NotBlank(message = "닉네임은 필수입니다.")
        @Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하여야 합니다.")
        String nickname) {
}
