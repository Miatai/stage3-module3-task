package com.mjc.school.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ReadByNewsIdSevice;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;

@Controller
public class AuthorController implements BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> {
  private final BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> authorService;
  private final ReadByNewsIdSevice<AuthorDtoResponse, Long> authorReadByNewsIdService;

  @Autowired
  public AuthorController(
      final BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> authorService,
      final ReadByNewsIdSevice< AuthorDtoResponse, Long> authorReadByNewsIdService) {
    this.authorService = authorService;
    this.authorReadByNewsIdService = authorReadByNewsIdService;
  }

  @Override
  @CommandHandler(operation = 6)
  public List<AuthorDtoResponse> readAll() {
    return authorService.readAll();
  }

  @Override
  @CommandHandler(operation = 7)
  public AuthorDtoResponse readById(@CommandParam(name = "id") Long id) {
    return authorService.readById(id);
  }

  @Override
  @CommandHandler(operation = 8)
  public AuthorDtoResponse create(@CommandBody AuthorDtoRequest dtoRequest) {
    return authorService.create(dtoRequest);
  }

  @Override
  @CommandHandler(operation = 9)
  public AuthorDtoResponse update(@CommandBody AuthorDtoRequest dtoRequest) {
    return authorService.update(dtoRequest);
  }

  @Override
  @CommandHandler(operation = 10)
  public boolean deleteById(@CommandParam(name = "id") Long id) {
    return authorService.deleteById(id);
  }

  @CommandHandler(operation = 16)
  public List<AuthorDtoResponse> readByNewsId(@CommandParam(name = "newsId") Long newsId) {
    return authorReadByNewsIdService.readByNewsId(newsId);
  }
}
