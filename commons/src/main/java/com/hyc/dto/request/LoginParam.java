package com.hyc.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: yche
 * @Date: 2019/07/30
 * @Description
 */
@Data
public class LoginParam {

    @NotBlank(message = "username不能为空")
    public String username;

    @NotBlank(message = "password不能为空")
    public String password;

}
