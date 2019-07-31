package com.hyc.controller;

import com.hyc.dto.request.LoginParam;
import com.hyc.dto.response.UserInfoResp;
import com.hyc.result.Result;
import com.hyc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @Author: yche
 * @Date: 2019/07/30
 * @Description
 */
@RestController
@Api(tags = "UserController-API", description = "用户相关接口")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    @ApiOperation(value = "用户登录接口", httpMethod = "POST", response = Result.class)
    public Result login(@Valid @RequestBody LoginParam param){
       return userService.login(param);
    }


    @GetMapping("/user")
    @ResponseBody
    @RequiresRoles("admin")
    public Result<UserInfoResp> findUserInfo(@RequestParam @Valid
                                             @NotBlank(message = "userName不能为空") String userName){
        return userService.findUserInfo(userName);
    }

}
