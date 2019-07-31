package com.hyc.service;

import com.hyc.dto.request.LoginParam;
import com.hyc.dto.response.UserInfoResp;
import com.hyc.result.Result;

/**
 * @Author: yche
 * @Date: 2019/07/30
 * @Description
 */
public interface UserService {

    Result login(LoginParam param);

    Result<UserInfoResp> findUserInfo(String username);
}
