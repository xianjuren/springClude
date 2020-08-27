package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigServer    //开启配置中心
public class ConfigServiceApplication9006 {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServiceApplication9006.class, args);
    }
}
