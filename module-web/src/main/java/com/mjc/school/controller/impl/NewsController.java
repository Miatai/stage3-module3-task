package com.mjc.school.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.NewsParamsDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.impl.NewsService;

@Controller
public class NewsController implements BaseController<NewsDtoRequest, NewsDtoResponse, Long> {
  private final BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService;

  @Autowired
  public NewsController(final BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService) {
    this.newsService = newsService;
  }

  @Override
  @CommandHandler(operation = 1)
  public List<NewsDtoResponse> readAll() {
    return newsService.readAll();
  }

  @Override
  @CommandHandler(operation = 2)
  public NewsDtoResponse readById(@CommandParam(name = "id") Long id) {
    return newsService.readById(id);
  }

  @Override
  @CommandHandler(operation = 3)
  public NewsDtoResponse create(@CommandBody NewsDtoRequest dtoRequest) {
    return newsService.create(dtoRequest);
  }

  @Override
  @CommandHandler(operation = 4)
  public NewsDtoResponse update(@CommandBody NewsDtoRequest dtoRequest) {
    return newsService.update(dtoRequest);
  }

  @Override
  @CommandHandler(operation = 5)
  public boolean deleteById(@CommandParam(name = "id") Long id) {
    return newsService.deleteById(id);
  }

  @CommandHandler(operation = 16)
  public List<AuthorDtoResponse> readAuthorByNewsId(@CommandParam(name = "newsId") Long newsId) {
    return ((NewsService)newsService).readAuthorByNewsId(newsId);
  }

  @CommandHandler(operation = 17)
  public List<TagDtoResponse> readTagsByNewsId(@CommandParam(name = "newsId") Long newsId) {
    return ((NewsService)newsService).readTagsByNewsId(newsId);
  }

  @CommandHandler(operation = 18)
  public List<NewsDtoResponse> readByParams(@CommandBody NewsParamsDtoRequest params) {
    return ((NewsService)newsService).readByParams(params);
  }
}
