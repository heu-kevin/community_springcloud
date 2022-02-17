package com.swim.community;

import com.swim.community.config.FeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient  // 在服务启动后自动注册到Eureka中
@EnableDiscoveryClient // 服务发现
@EnableFeignClients
public class RedisProvider8003 {
    public static void main(String[] args) {
        SpringApplication.run(RedisProvider8003.class, args);
    }
}
