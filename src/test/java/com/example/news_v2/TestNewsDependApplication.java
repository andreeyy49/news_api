package com.example.news_v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestNewsDependApplication {

    public static void main(String[] args) {
        SpringApplication.from(TestNewsDependApplication::main).with(TestNewsDependApplication.class).run(args);
    }
}
