package com.hes.test.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CRUDService<E> {

    List<E> findAll(Pageable pageable);

    List<E> findAll();

    E findById(Long id);

    void deleteById(Long id);

    E update(E dto);

    Long save(E dto);
}
