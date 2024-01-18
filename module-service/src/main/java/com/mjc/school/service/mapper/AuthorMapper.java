package com.mjc.school.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.mjc.school.repository.model.Author;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

  List<AuthorDtoResponse> modelListToDtoList(List<Author> modelList);

  AuthorDtoResponse modelToDto(Author model);

  @Mappings({
    @Mapping(target = "createDate", ignore = true),
    @Mapping(target = "lastUpdatedDate", ignore = true),
    @Mapping(target = "news", ignore = true),
  })
  Author dtoToModel(AuthorDtoRequest dto);
}
