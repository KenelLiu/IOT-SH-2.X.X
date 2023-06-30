package com.iot.example.spiredoc;
import com.iot.sh.model.HttpResponse;
import com.iot.sh.util.encrypt.MD5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kenel Liu
 */
@Slf4j
@SpringBootApplication
public class SpireDocApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpireDocApplication.class, args);
    }
}
