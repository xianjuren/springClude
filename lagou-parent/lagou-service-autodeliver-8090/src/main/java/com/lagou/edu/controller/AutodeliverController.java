package com.lagou.edu.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/autodeliver")
public class AutodeliverController {

    @Autowired
    private RestTemplate restTemplate;
    // http://localhost:8090/autodeliver/checkstate/1545132
//    @GetMapping("/checkstate/{userId}")
//    public Integer findResumeOpenState(@PathVariable Long userId) {
//        //调用远程服务 -》简历微服务接口  RestTemplate 调用远程服务类，类似于JdbcTemplate
//
//        Integer forObject = restTemplate.getForObject("http://localhost:8080/resume/openstate/" + userId, Integer.class);
//        return forObject;
//    }

    /**
     * 改造注册到Eureka服务中心后的获取方法
     *
     * @param userId
     * @return
     */
    @Autowired
    private DiscoveryClient discoveryClient; //注入客户端

//    @GetMapping("/checkstate/{userId}")
////    public Integer findResumeOpenState(@PathVariable Long userId) {
////
////        //1。从Eureka服务中心获取我们关注的那个服务实例的信息以及接口信息,
////        List<ServiceInstance> instances = discoveryClient.getInstances("lagou-service-resume");//服务名称：application.name
////        //2. 如果有多个实例，选择其中一个（负载均衡过程）
////        ServiceInstance instance = instances.get(0);
////        //3.从元数据中获取host port
////        String host = instance.getHost();
////        int port = instance.getPort();
////        String url = "http://" + host + ":" + port + "/resume/openstate/";
////        System.out.println("=============>从Eureka集群中获取到的服务拼接实例："+url);
////        //4.调用远程服务 -》简历微服务接口  RestTemplate 调用远程服务类，类似于JdbcTemplate
////        Integer forObject = restTemplate.getForObject(url + userId, Integer.class);
////        return forObject;
////    }

    /**
     * 使用Ribbon负载均衡
     *
     * @param userId
     * @return
     */

    @GetMapping("/checkstate/{userId}")
    public Integer findResumeOpenState(@PathVariable Long userId) {
        //使用ribbon不需要我们自己获取服务实例然后选择一个去访问（自己会选择）
        String url = "http://lagou-service-resume/resume/openstate/" + userId;//指定服务名
        System.out.println("=============>从Eureka集群中获取到的服务拼接实例：" + url);
        Integer forObject = restTemplate.getForObject(url, Integer.class);
        return forObject;

    }

    /**
     * 提供者模拟处理超时，调用方法添加Hystrix
     *
     * @param userId
     * @return
     */
    //使用HystrixCommand注解进行熔断控制
    @HystrixCommand(
            //线程池标示，保持唯一，不唯一就共用了
            threadPoolKey = "findResumeOpenStateTimeout",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "1"),//线程数
                    @HystrixProperty(name = "maxQueueSize", value = "20") //等待队列长度
            },
            //commandProperties 熔断的一些细节属性配置
            commandProperties = {
                    //每一个属性都是一个HystrixProperty
                    //name属性所在类：HystrixCommandProperties
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            }
    )
    @GetMapping("/checkStateTimeout/{userId}")
    public Integer findResumeOpenStateTimeout(@PathVariable Long userId) {
        String url = "http://lagou-service-resume/resume/openstate/" + userId;//指定服务名
        Integer forObject = restTemplate.getForObject(url, Integer.class);
        return forObject;
    }


    @HystrixCommand(
            //线程池标示，保持唯一，不唯一就共用了
            threadPoolKey = "findResumeOpenStateTimeoutFallback",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "2"),//线程数
                    @HystrixProperty(name = "maxQueueSize", value = "20") //等待队列长度
            },
            //commandProperties 熔断的一些细节属性配置
            commandProperties = {
                    //每一个属性都是一个HystrixProperty
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    //hystrix高级配置，定制工作过程细节
                    //统计时间窗口定义
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "8000"),
                    //统计时间窗口的最小请求数
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
                    //统计时间窗口内的错误数量百分比阈值
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    //自我修复的活动窗口长度
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "3000"),
            },
            fallbackMethod = "myFallBack" //回退方法
    )
    @GetMapping("/checkStateTimeoutFallback/{userId}")
    public Integer findResumeOpenStateTimeoutFallback(@PathVariable Long userId) {
        String url = "http://lagou-service-resume/resume/openstate/" + userId;//指定服务名
        Integer forObject = restTemplate.getForObject(url, Integer.class);
        return forObject;
    }

    /**
     * 定义回退方法，返回预设默认值，注意方法形参和返回值与原始方法保持一致
     * 可以在类上使用@DefaultProperties注解统一指定整个类中共用的降级(兜底)方法
     *
     * @param userId
     * @return
     */
    public Integer myFallBack(Long userId) {
        return -123333;//兜底数据
    }
}
