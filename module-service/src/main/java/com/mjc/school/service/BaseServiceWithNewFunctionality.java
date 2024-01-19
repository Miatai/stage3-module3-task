package com.mjc.school.service;

import java.util.List;

public interface BaseServiceWithNewFunctionality<T, R, K, S, U, V> extends BaseService<T, R, K>{
        List<R> readByParams(S params);
        List<U> readAuthorByNewsId(K newsId);
        List<V> readTagsByNewsId(K newsId);
}
