package com.iot.sh.web.mapper;

import java.util.List;

/**
 * @author Kenel Liu
 */
public interface BaseMapper<T,K>{

    public T findByKey(K id);

    public T findByCondition(T entity);

    public int add(T entity);

    public int update(T entity);

    public T findDeleteCondition(T entity);

    public int deleteByKey(T entity);

    public long getCount(T entity);

    public List<T> getListModel(T entity);
}
