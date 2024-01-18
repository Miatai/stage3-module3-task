package com.mjc.school.service;

import java.util.List;

public interface ReadByNewsIdSevice<R, K> {
    List<R> readByNewsId(K newsId);
}
