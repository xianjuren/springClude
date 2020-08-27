package com.lagou.edu.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigController {

    //和获取本地信息一样
    @Value("${lagou.message}")
    private String lagouMessage;

    @GetMapping("/viewconfig")
    public String viewconfig() {

        return "lagouMessage=============>" + lagouMessage;
    }
}
