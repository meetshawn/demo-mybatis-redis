package com.example.demomybatisredis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.demomybatisredis.mapper")
public class DemoMybatisRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMybatisRedisApplication.class, args);
    }

}
