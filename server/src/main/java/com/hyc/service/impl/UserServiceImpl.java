package com.hyc.service.impl;

import com.hyc.config.jwt.JwtHelper;
import com.hyc.config.shiro.cache.AuthenCustomCache;
import com.hyc.config.shiro.cache.AuthorCustomCache;
import com.hyc.dto.request.LoginParam;
import com.hyc.dto.response.UserInfoResp;
import com.hyc.entity.User;
import com.hyc.repositroy.UserRepository;
import com.hyc.result.Result;
import com.hyc.result.ResultCode;
import com.hyc.service.UserService;
import com.hyc.utils.EncryptUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author: yche
 * @Date: 2019/07/30
 * @Description
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AuthenCustomCache authenCustomCache;

    @Autowired
    private AuthorCustomCache authorCustomCache;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public Result login(LoginParam param) {
        User user = userRepository.findByUsername(param.getUsername());
        if(user != null && StringUtils.equals(EncryptUtils.encryptPassword(param.getPassword(), user.getCredential()), user.getPassword())) {
            authenCustomCache.remove(param.getUsername());
            authorCustomCache.remove(param.getUsername());

            return Result.ok(jwtHelper.generateToken(user.getUsername()));
        }

        return Result.fail(ResultCode.LOGIN_FAIL);
    }

    @Override
    public Result<UserInfoResp> findUserInfo(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null){
            return Result.fail(ResultCode.USERNAME_NOT_EXIST);
        }
        return Result.ok(UserInfoResp.toDto(user));
    }


}
