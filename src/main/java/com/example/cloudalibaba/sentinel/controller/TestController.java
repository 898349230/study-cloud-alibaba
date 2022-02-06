package com.example.cloudalibaba.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/")
public class TestController {

    @RequestMapping("hello")
    @SentinelResource(value = "hello")
    public String sayHello(String str){
        System.out.println("hello : " + str);
        return str;
    }
    @RequestMapping("hello2")
    @SentinelResource(value = "hello2")
    public String sayHello2(){
//        System.out.println(str);
        return "str";
    }
}
