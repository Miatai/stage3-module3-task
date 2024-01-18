package com.mjc.school.service.impl;

import static com.mjc.school.service.exceptions.ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mapper.AuthorMapper;
import com.mjc.school.service.validator.Valid;

@Service("authorService")
public class AuthorService implements BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> {

  private final BaseRepository<Author, Long> authorRepository;
  private final AuthorMapper mapper;

  @Autowired
  public AuthorService(
      final BaseRepository<Author, Long> authorRepository,
      final AuthorMapper mapper) {
    this.authorRepository = authorRepository;
    this.mapper = mapper;
  }

  @Override
  public List<AuthorDtoResponse> readAll() {
    return mapper.modelListToDtoList(authorRepository.readAll());
  }

  @Override
  public AuthorDtoResponse readById(Long id) {
    return authorRepository
        .readById(id)
        .map(mapper::modelToDto)
        .orElseThrow(
            () -> new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), id)));
  }

  @Override
  public AuthorDtoResponse create(@Valid AuthorDtoRequest createRequest) {
    Author model = mapper.dtoToModel(createRequest);
    LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    model.setCreateDate(date);
    model.setLastUpdatedDate(date);
    model = authorRepository.create(model);
    return mapper.modelToDto(model);
  }

  @Override
  public AuthorDtoResponse update(@Valid AuthorDtoRequest updateRequest) {
    if (authorRepository.existById(updateRequest.id())) {
      Author model = mapper.dtoToModel(updateRequest);
      LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
      model.setLastUpdatedDate(date);
      model = authorRepository.update(model);
      return mapper.modelToDto(model);
    } else {
      throw new NotFoundException(
          String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id()));
    }
  }

  @Override
  public boolean deleteById(Long id) {
    if (authorRepository.existById(id)) {
      return authorRepository.deleteById(id);
    } else {
      throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), id));
    }
  }
}
