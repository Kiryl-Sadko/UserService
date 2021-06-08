package com.hes.test.builder;

public interface Builder<T> {

    T build();

    void reset();
}
