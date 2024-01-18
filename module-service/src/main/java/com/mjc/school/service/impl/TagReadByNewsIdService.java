package com.mjc.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjc.school.service.BaseService;
import com.mjc.school.service.ReadByNewsIdSevice;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;

@Service("tagReadByNewsIdService")
public class TagReadByNewsIdService implements ReadByNewsIdSevice<TagDtoResponse, Long> {
    private final BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService;
    private final BaseService<TagDtoRequest, TagDtoResponse, Long> tagService;

    @Autowired
    public TagReadByNewsIdService(final BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService,
    final BaseService<TagDtoRequest, TagDtoResponse, Long> tagService){
        this.newsService = newsService;
        this.tagService = tagService;
    }

    @Override
    public List<TagDtoResponse> readByNewsId(Long newsId) {
        NewsDtoResponse newsDto = newsService.readById(newsId);
        List<TagDtoResponse> tagsList =  List.copyOf(newsDto.tagDtos());
        return tagsList;
    }
    
}
