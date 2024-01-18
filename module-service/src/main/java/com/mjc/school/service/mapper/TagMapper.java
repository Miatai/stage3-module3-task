package com.mjc.school.service.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;

@Mapper(componentModel = "spring")
public interface TagMapper {
    List<TagDtoResponse> modelListToDtoList(List<Tag> modelList);

    Set<TagDtoResponse> modelSetToDtoSet(Set<Tag> modelSet);

    TagDtoResponse modelToDto(Tag model);

    @Mapping(target = "news", ignore = true)
    Tag dtoToModel(TagDtoRequest dto);

}
