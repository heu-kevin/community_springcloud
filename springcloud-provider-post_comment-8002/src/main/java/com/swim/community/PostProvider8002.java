package com.swim.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient  // 在服务启动后自动注册到Eureka中
@EnableDiscoveryClient // 服务发现
public class PostProvider8002 {
    public static void main(String[] args) {
        SpringApplication.run(PostProvider8002.class, args);
    }
}