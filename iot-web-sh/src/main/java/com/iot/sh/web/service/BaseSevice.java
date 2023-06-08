package com.iot.sh.web.service;

import java.util.List;

/**
 * @author Kenel Liu
 */
public interface BaseSevice<T,K>{
   default public T findByKey(K id){
       return null;
   }

   default public T findByCondition(T entity){
       return null;
   }

   default public int add(T entity){
       return 0;
    }

    default public int update(T entity){
       return 0;
    }

    default public int update(T entity,boolean bQuery){
       return 0;
    }

    default public T findDeleteCondition(T entity){
       return null;
    }

    default public int deleteByKey(T entity){
       return 0;
    }

    default public long getCount(T entity){
       return 0;
    }

    default public List<T> getListModel(T entity){
       return null;
    }
}
