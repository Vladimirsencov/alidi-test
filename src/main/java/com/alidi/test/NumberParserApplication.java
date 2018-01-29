package com.alidi.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class NumberParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(NumberParserApplication.class, args);
    }
}
