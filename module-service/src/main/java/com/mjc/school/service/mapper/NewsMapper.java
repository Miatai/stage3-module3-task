package com.mjc.school.service.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;

@Mapper(componentModel = "spring")
public abstract class NewsMapper {

  @Autowired
  protected BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> authorService;

  @Autowired
  protected BaseService<TagDtoRequest, TagDtoResponse, Long> tagService;

  protected TagMapper tagMapper = Mappers.getMapper(TagMapper.class);

  public abstract List<NewsDtoResponse> modelListToDtoList(List<News> modelList);

  @Mapping(target = "authorDto", expression = "java(model.getAuthor() != null ? authorService.readById(model.getAuthor().getId()) : null)")
  @Mapping(target = "tagDtos", expression = "java(tagMapper.modelSetToDtoSet(model.getTags()))")
  public abstract NewsDtoResponse modelToDto(News model);

  @Mappings({
      @Mapping(target = "createDate", ignore = true),
      @Mapping(target = "lastUpdatedDate", ignore = true),
      @Mapping(target = "author", expression = "java(dto.authorId() != null ? authorIdToAuthorModel(dto.authorId()) : null)"),
      @Mapping(target = "tags", expression = "java(dto.tagIds() != null ? tagIdsSetToTagModelsSet(dto.tagIds()) : null)")
  })
  public abstract News dtoToModel(NewsDtoRequest dto);

  protected Author authorIdToAuthorModel(Long authorId) {
    Author authorModel;
    AuthorDtoResponse authorDtoResponse = authorService.readById(authorId);
    authorModel = new Author(authorDtoResponse.id(), authorDtoResponse.name(), authorDtoResponse.createDate(),
        authorDtoResponse.lastUpdatedDate());
    return authorModel;
  }

  protected Set<Tag> tagIdsSetToTagModelsSet(Set<Long> tagIds) {
        Set<Tag> tagModelsSet = new HashSet<>();
        for (Long id : tagIds) {
            TagDtoResponse dtoResponse = tagService.readById(id);
            tagModelsSet.add(new Tag(dtoResponse.id(), dtoResponse.name()));
        }
        return tagModelsSet;
    }
}
