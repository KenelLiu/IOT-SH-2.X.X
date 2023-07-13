package iot.sh.spring.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
在application.yml配置类似如下
 upload:
    path: ./file
    fileParentPath[0]: sign
    fileParentPath[1]: pic
    fileParentPath[2]: video
    picMap:
        jpg: image/jpeg
        jpeg: image/jpeg
        jpe: image/jpeg
        png: image/png
        tiff: image/tiff
        svg: image/svg+xml
        bmp: image/bmp
        gif: image/gif
        3gp: video/3gpp
        mp4: video/mp4
        mp4v: video/mp4
        mpg4: video/mp4
        pdf: application/pdf
 * @author Kenel Liu
 */
@ConfigurationProperties(prefix = "upload")
@Data
public class UploadConfig {
    private String path;
    private Map<String,String> picMap = new HashMap<>();
    private List<String> fileParentPath=new ArrayList<>();
}
