package com.mjc.school.service.dto;

import java.util.Set;

import com.mjc.school.service.validator.constraint.Size;

public record NewsParamsDtoRequest(
    String title,
    String content,
    String authorName,
    Set<Long> tagIds,
    Set<String> tagNames
) {
    
}
