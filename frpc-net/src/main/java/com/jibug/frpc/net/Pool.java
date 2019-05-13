package com.jibug.frpc.net;

import java.util.List;

/**
 * @author heyingcai
 */
public interface Pool<T> {

    void add(T t);

    T get();

    List<T> getAll();

    int size();

    boolean isEmpty();

    boolean contains(T t);

    void remove(T t);
}
