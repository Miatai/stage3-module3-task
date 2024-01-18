package com.mjc.school.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ReadByParams;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.NewsParamsDtoRequest;

@Controller
public class NewsController implements BaseController<NewsDtoRequest, NewsDtoResponse, Long> {

  private final BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService;
  private final ReadByParams<NewsDtoResponse, NewsParamsDtoRequest> newsReadByParamsService;

  @Autowired
  public NewsController(final BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService,
  final ReadByParams<NewsDtoResponse, NewsParamsDtoRequest> newsReadByParamsService) {
    this.newsService = newsService;
    this.newsReadByParamsService = newsReadByParamsService;
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

  @CommandHandler(operation = 18)
  public List<NewsDtoResponse> readByParams(@CommandBody NewsParamsDtoRequest params) {
    return newsReadByParamsService.readByParams(params);
  }
}
