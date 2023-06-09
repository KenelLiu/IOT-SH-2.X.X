package com.reactor.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.*;
import reactor.tools.agent.ReactorDebugAgent;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;

//https://github.com/Van-Dame/medium-micronaut-r2dbc-example
//java -Dspring.config.location=file:./application.yml -jar bodyiot-1.0.0.jar
@EnableAutoConfiguration(exclude = {R2dbcAutoConfiguration.class})
@ComponentScan(basePackages={"com.cdo.example"})
@Log4j2
public class ExampleApplication {
   // private static Log logger= LogFactory.getLog(ExampleApplication.class);

    public static void main(String[] args) throws FileNotFoundException, IOException {
        ReactorDebugAgent.init();
        //Schedulers.enableMetrics();
        ApplicationContext applicationContext= SpringApplication.run(ExampleApplication.class, args);
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Setter
    @Getter
    @AllArgsConstructor
    static
    class Person{
        private String id;
        private String name;
    }


    public static String alphabet(int letterNumber) {
        if (letterNumber < 1 || letterNumber > 26) {
            return null;
        }
        int letterIndexAscii = 'A' + letterNumber - 1;
        return "" + (char) letterIndexAscii;
    }

    private static Mono<Void> thenTask1() {
        return Mono.create(sink -> {
            System.out.println("Performing then task 1");
            sink.success();
        });
    }

    private static Mono<Void> thenTask2() {
        return Mono.create(sink -> {
            System.out.println("Performing then task 2");
            sink.success();
        });
    }

    private static  String thenReturnTask() {
        System.out.println("Performing then return task");
        return "Welcome to this example";
    }
}
