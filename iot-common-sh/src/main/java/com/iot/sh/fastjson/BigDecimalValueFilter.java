package com.iot.sh.fastjson;

import com.alibaba.fastjson.serializer.ValueFilter;

import java.math.BigDecimal;

public class BigDecimalValueFilter implements ValueFilter {

    @Override
    public Object process(Object object, String name, Object value) {
        if(value instanceof BigDecimal) {
            BigDecimal bigDecimal = ((BigDecimal)value).stripTrailingZeros();
            return new BigDecimal(bigDecimal.toPlainString());
        }
        return value;
    }

}