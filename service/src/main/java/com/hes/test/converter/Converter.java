package com.hes.test.converter;

import com.hes.test.dto.Dto;
import com.hes.test.entity.Entity;

public interface Converter<E extends Entity, D extends Dto> {

    E convertToEntity(D dto);

    D convertToDto(E entity);

}
