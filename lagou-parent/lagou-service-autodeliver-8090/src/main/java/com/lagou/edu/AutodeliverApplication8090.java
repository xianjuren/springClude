package com.lagou.edu;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient //开启注册中心的客户端，推荐使用，通用模型，它可以开启Eureka,Nacos
//@EnableHystrix //开启Hystrix功能,
@EnableCircuitBreaker //开启熔断 ，色格特不瑞哥，包含了@EnableHystrix功能

//@SpringCloudApplication //等同于 @SpringBootApplication + @EnableDiscoveryClient+@EnableCircuitBreaker
public class AutodeliverApplication8090 {

    public static void main(String[] args) {
        SpringApplication.run(AutodeliverApplication8090.class, args);
    }

    //注入对象，使用RestTemplate模板对象进行远程调用
    @Bean
    @LoadBalanced //开启负载均衡
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    /**
     * 在被监控的微服务中心注册一个servlet,后期我们通过这个servlet来获取该服务的 Hystrix监控数据。
     * 前提：被监控的微服务需要引入springboot的actuator功能，已在父工程中集成
     * @return
     */
    @Bean
    public ServletRegistrationBean getServlet(){
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}
