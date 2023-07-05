package com.iot.example.spiredoc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author Kenel Liu
 */
@Slf4j
@SpringBootApplication
public class Doc2PdfApplication {
    public static void main(String[] args) {
        SpringApplication.run(Doc2PdfApplication.class, args);
    }

}
