package com.mjc.school.service.mapper;

import org.mapstruct.Mapper;

import com.mjc.school.repository.utils.NewsParams;
import com.mjc.school.service.dto.NewsParamsDtoRequest;

@Mapper(componentModel = "spring")
public interface NewsParamsMapper {
        NewsParams dtoToModel(NewsParamsDtoRequest dtoRequest);
}
