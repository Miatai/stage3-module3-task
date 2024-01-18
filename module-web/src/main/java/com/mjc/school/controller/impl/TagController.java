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
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;

@Controller
public class TagController implements BaseController<TagDtoRequest, TagDtoResponse, Long> {
    private final BaseService<TagDtoRequest, TagDtoResponse, Long> tagService;
    private final ReadByNewsIdSevice<TagDtoResponse, Long> tagReadByNewsIdService;

    @Autowired
    public TagController(
            final BaseService<TagDtoRequest, TagDtoResponse, Long> tagService,
            final ReadByNewsIdSevice<TagDtoResponse, Long> tagReadByNewsIdService) {
        this.tagService = tagService;
        this.tagReadByNewsIdService = tagReadByNewsIdService;
    }

    @Override
    @CommandHandler(operation = 11)
    public List<TagDtoResponse> readAll() {
        return tagService.readAll();
    }

    @Override
    @CommandHandler(operation = 12)
    public TagDtoResponse readById(@CommandParam(name = "id") Long id) {
        return tagService.readById(id);
    }

    @Override
    @CommandHandler(operation = 13)
    public TagDtoResponse create(@CommandBody TagDtoRequest createRequest) {
        return tagService.create(createRequest);
    }

    @Override
    @CommandHandler(operation = 14)
    public TagDtoResponse update(@CommandBody TagDtoRequest updateRequest) {
        return tagService.update(updateRequest);
    }

    @Override
    @CommandHandler(operation = 15)
    public boolean deleteById(@CommandParam(name = "id") Long id) {
        return tagService.deleteById(id);
    }

    @CommandHandler(operation = 17)
    public List<TagDtoResponse> readByNewsId(@CommandParam(name = "newsId") Long newsId) {
        return tagReadByNewsIdService.readByNewsId(newsId);
    }
}
