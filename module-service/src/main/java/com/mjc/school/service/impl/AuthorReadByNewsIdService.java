package com.mjc.school.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjc.school.service.BaseService;
import com.mjc.school.service.ReadByNewsIdSevice;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;

@Service("authorReadByNewsIdService")
public class AuthorReadByNewsIdService implements ReadByNewsIdSevice<AuthorDtoResponse, Long> {
    private final BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService;
    private final BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> authorService;

    @Autowired
    public AuthorReadByNewsIdService(
            final BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService,
            final BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> authorService) {
        this.newsService = newsService;
        this.authorService = authorService;
    }

    @Override
    public List<AuthorDtoResponse> readByNewsId(Long newsId) {
        NewsDtoResponse newsDto = newsService.readById(newsId);
        List<AuthorDtoResponse> authorsList = new ArrayList<>();
        authorsList.add(newsDto.authorDto());
        return authorsList;
    }
}
