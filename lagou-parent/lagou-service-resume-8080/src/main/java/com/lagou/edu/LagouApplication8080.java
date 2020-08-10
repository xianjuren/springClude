package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EntityScan("com.lagou.edu.pojo")
//@EnableEurekaClient //Eureka Client 只开启Eureka
@EnableDiscoveryClient //开启注册中心的客户端，推荐使用，通用模型，它可以开启Eureka,Nacos
    //从Egdware版本开始，不添加以上开启客户端的主页，也可以使用，建议开启
public class LagouApplication8080 {


    public static void main(String[] args) {
        SpringApplication.run(LagouApplication8080.class, args);
    }
}
