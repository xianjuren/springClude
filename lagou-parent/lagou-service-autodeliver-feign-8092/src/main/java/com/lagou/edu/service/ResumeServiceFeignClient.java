package com.lagou.edu.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
//原来：http://lagou-service-resume/resume/openstate/" + userId
//value 配置指定的要访问的服务名称（登记到服务中心的服务名称）
@FeignClient(value = "lagou-service-resume")
@RequestMapping("/resume")
public interface ResumeServiceFeignClient {



    //参照8080，远程调用服务的接口
    //Feign要做的事情就是，拼接url发起的请求
    //调用该方法就是调用本地接口，实际是调用远程接口
    @GetMapping("/openstate/{userId}")
    public Integer findResumeOpenState(@PathVariable("userId") Long userId);

}
