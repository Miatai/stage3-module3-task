package com.mjc.school.repository;

import java.util.List;

import com.mjc.school.repository.model.BaseEntity;

public interface BaseRepositoryWithNewFunctionality<T extends BaseEntity<K>, K, S> extends BaseRepository<T , K>{
    List<T> readByParams(S params);

}
