package com.study.community.common;

import java.util.List;

public record ValidationErrorResponse(String message, List<FieldErrorResponse> errors) {
}
