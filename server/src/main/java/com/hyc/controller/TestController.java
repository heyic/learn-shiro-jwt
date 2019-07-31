package com.hyc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "测试API", description = "测试API的描述")
public class TestController {


    @ApiOperation(value = "向外界说hello", response = String.class)
    @PostMapping("/hello")
    public String helloworld(){
        return "hello world";
    }


}
