package com.iot.sh.util.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;
import java.util.Map;

/**
 * @author Kenel Liu
 */
public class JsonObjectUtil {
    /**
     *  合并JSON,用source覆盖target,返回覆盖后的JSON
     * @param source
     * @param target 被覆盖JSON
     * @return
     */
    public static ObjectNode merge(ObjectNode source, ObjectNode target){
        if(target==null)return source;
        if(source==null)return target;
        for(Iterator<Map.Entry<String, JsonNode>> it=source.fields();it.hasNext();){
            Map.Entry<String,JsonNode> entry=it.next();
            String key=entry.getKey();
            JsonNode value=entry.getValue();
            if(!target.has(key)){
                target.put(key,value);
            }else{
                Object targetValue=target.get(key);
                if(value instanceof ObjectNode &&
                        targetValue instanceof ObjectNode){
                    ObjectNode valueSourceJSON=(ObjectNode)value;
                    ObjectNode valueTargetJSON=(ObjectNode)targetValue;
                    ObjectNode mergeValue=merge(valueSourceJSON,valueTargetJSON);
                    target.put(key,mergeValue);
                }else if(value instanceof ArrayNode &&
                        targetValue instanceof ArrayNode){
                    ArrayNode valueSourceArr=(ArrayNode)value;
                    ArrayNode valueTargetArr=(ArrayNode)value;
                    merge(target,key,valueSourceArr,valueTargetArr);
                }else{
                    target.put(key,value);
                }
            }
        }

        return  target;
    }

    public static ObjectNode merge(ObjectNode target, String key, ArrayNode subSourceArr, ArrayNode subTargetArr){
        if(subSourceArr.size()==0) return target;
        if(subTargetArr.size()==0){
            target.put(key,subSourceArr);
            return target;
        }
        //=====判断类型=======//
        Object valueSubSrouce=subSourceArr.get(0);
        Object valueSubTarget=subSourceArr.get(0);
        if(valueSubSrouce instanceof ObjectNode
                && valueSubTarget instanceof ObjectNode){
            int k=0;
            for(int i=0;i<subSourceArr.size();i++){
                ObjectNode sourceObj=(ObjectNode)(subSourceArr.get(i));
                if(k<subTargetArr.size()){
                    ObjectNode targetObj=(ObjectNode)(subTargetArr.get(i));
                    ObjectNode mergeObj=merge(sourceObj,targetObj);
                    ((ArrayNode)target.get(key)).set(k,mergeObj);
                    k++;
                }else{
                    ((ArrayNode)target.get(key)).add(sourceObj);
                }
            }
        }else{
            //=========普通类型 直接替换======//
            target.put(key,subSourceArr);
        }
        return target;
    }
}
