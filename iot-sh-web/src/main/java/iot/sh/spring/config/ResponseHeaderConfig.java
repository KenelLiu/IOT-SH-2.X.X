package iot.sh.spring.config;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 在application.yml配置类似如下
 http:
    response:
        headers:
            Access-Control-Allow-Methods: "GET,POST,DELETE,PUT,PATCH,OPTIONS"
            Access-Control-Allow-Credentials: "true"
            Access-Control-Allow-Origin: "*"
            Access-Control-Allow-Headers: "*"
 * @author Kenel Liu
 */
@Data
public class ResponseHeaderConfig {
    private Map<String,String> headers = new LinkedHashMap<>();
}
