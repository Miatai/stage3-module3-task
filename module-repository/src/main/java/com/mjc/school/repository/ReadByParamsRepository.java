package com.mjc.school.repository;

import java.util.List;

import com.mjc.school.repository.model.BaseEntity;

public interface ReadByParamsRepository <T extends BaseEntity<K>,R,K>{
    List<T> readByParams(R params);
}
