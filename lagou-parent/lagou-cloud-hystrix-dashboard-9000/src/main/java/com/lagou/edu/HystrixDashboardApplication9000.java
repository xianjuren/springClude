package com.lagou.edu;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient //开启注册中心的客户端
@EnableHystrixDashboard //开启熔断监控 hystrix dashboard
public class HystrixDashboardApplication9000 {

    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardApplication9000.class,args);
    }


}
