package com.mjc.school.service.dto;

import java.util.Set;

import com.mjc.school.service.validator.constraint.Max;
import com.mjc.school.service.validator.constraint.Min;
import com.mjc.school.service.validator.constraint.NotNull;
import com.mjc.school.service.validator.constraint.Size;

public record NewsDtoRequest(
    @Min(1)
    @Max(Long.MAX_VALUE)
    Long id,

    @NotNull
    @Size(min = 5, max = 30)
    String title,

    @NotNull
    @Size(min = 5, max = 255)
    String content,
    Long authorId,
    Set<Long> tagIds) {
}
