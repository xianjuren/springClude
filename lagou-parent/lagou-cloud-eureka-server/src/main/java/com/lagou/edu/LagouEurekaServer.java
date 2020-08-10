package com.lagou.edu;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
//声明当前项目为Eureka服务
@EnableEurekaServer
public class LagouEurekaServer {

    public static void main(String[] args) {
        SpringApplication.run(LagouEurekaServer.class,args);
        try {
            System.out.println("ip地址："+ InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
