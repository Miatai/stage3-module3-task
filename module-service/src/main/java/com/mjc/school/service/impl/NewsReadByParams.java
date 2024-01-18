package com.mjc.school.service.impl;

import static com.mjc.school.service.exceptions.ServiceErrorCode.NEWS_WITH_SUCH_PARAMS_NOT_EXIST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjc.school.repository.ReadByParamsRepository;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.utils.NewsParams;
import com.mjc.school.service.ReadByParams;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.NewsParamsDtoRequest;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.mapper.NewsParamsMapper;

@Service("newsReadByParamsService")
public class NewsReadByParams implements ReadByParams<NewsDtoResponse, NewsParamsDtoRequest> {
    private final ReadByParamsRepository<News, NewsParams, Long> newsReadByParamsRepository;
    private final NewsParamsMapper newsParamsMapper;
    private final NewsMapper newsMapper;

    @Autowired
    public NewsReadByParams(final ReadByParamsRepository<News, NewsParams, Long> newsReadByParamsRepository,
            final NewsParamsMapper newsParamsMapper,
            final NewsMapper newsMapper) {
        this.newsReadByParamsRepository = newsReadByParamsRepository;
        this.newsParamsMapper = newsParamsMapper;
        this.newsMapper = newsMapper;

    }

    @Override
    public List<NewsDtoResponse> readByParams(NewsParamsDtoRequest params) {
        List<News> news = newsReadByParamsRepository.readByParams(newsParamsMapper.dtoToModel(params));
        if (news.isEmpty()) {
            throw new NotFoundException(
                    String.format(NEWS_WITH_SUCH_PARAMS_NOT_EXIST.getMessage()));
        }
        return newsMapper.modelListToDtoList(news);
    }
}
