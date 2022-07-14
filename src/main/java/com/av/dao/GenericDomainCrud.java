package com.av.dao;

import java.util.List;

public interface GenericDomainCrud<K, T> {
    T save(T domain);

    void delete(T domain);

    List<T> getAll();

}
