package com.hyc.dto.response;

import com.hyc.entity.User;
import lombok.Data;

/**
 * @Author: yche
 * @Date: 2019/07/30
 * @Description
 */
@Data
public class UserInfoResp {

    private String username;

    private String email;

    public static UserInfoResp toDto(User user){
        UserInfoResp userInfoResp = new UserInfoResp();
        userInfoResp.setEmail(user.getEmail());
        userInfoResp.setUsername(user.getUsername());
        return  userInfoResp;
    }

}
