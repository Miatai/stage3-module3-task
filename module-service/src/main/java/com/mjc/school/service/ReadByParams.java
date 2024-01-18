package com.mjc.school.service;

import java.util.List;

public interface ReadByParams <R,K>{
     List<R> readByParams(K params);
}
