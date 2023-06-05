package com.iot.sh.util.entity;

import com.iot.sh.util.common.StringUtil;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Method;

/**
 * KenelLiu
 */
@Slf4j
public class EntityUtil {

    /**
     * @param entity 实体类
     * @param fieldName 字段名
     * @param entityFieldType 字段类型
     */
    @SuppressWarnings("unchecked")
    public  static<T,S>  S getFieldValue(T entity,String fieldName,S entityFieldType){
        try{
            if(entity==null) return null;
            Class<?>  cls=entity.getClass();
            Method method = cls.getDeclaredMethod("get"+ StringUtil.getFirstAlphabetUpper(fieldName));
            Object value=method.invoke(entity);
            if(value==null) return null;
            return (S)value;
        }catch(Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return null;
    }

    public  static<T>  Object getFieldValue(T entity,String fieldName){
        try{
            if(entity==null) return null;
            Class<?>  cls=entity.getClass();
            Method method = cls.getDeclaredMethod("get"+ StringUtil.getFirstAlphabetUpper(fieldName));
            Object value=method.invoke(entity);
            if(value==null) return null;
            return value;
        }catch(Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return null;
    }
}
