package com.hyc.config.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyc.config.jwt.JwtHelper;
import com.hyc.constant.Consts;
import com.hyc.result.Result;
import com.hyc.result.ResultCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Author: yche
 * @Date: 2019/07/29
 * @Description Jwt验证过滤器
 */
public class StatelessAuthcFilter extends AccessControlFilter {

    private  ObjectMapper objectMapper;

    private  JwtHelper jwtHelper;

    private  RedisTemplate redisTemplate;

    public StatelessAuthcFilter(ObjectMapper objectMapper, JwtHelper jwtHelper, RedisTemplate redisTemplate) {
        this.objectMapper = objectMapper;
        this.jwtHelper = jwtHelper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = jwtHelper.getToken(request);
        if (StringUtils.isBlank(token)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            objectMapper.writeValue(response.getWriter(), Result.fail(ResultCode.UNAUTHORIZED));
            return false;
        }
        //判断token是否过期以及是否能被解析
        boolean tokenExpired = false;
        Claims claims = null;
        try {
            claims = jwtHelper.getClaimsFromToken(token);
        } catch (ExpiredJwtException | SignatureException e) {
            tokenExpired = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        if (tokenExpired) {
            //当前token已过期并且尚可刷新，刷新token并放行；当前token无可刷新记录或已过可刷新时间，需要重新登录
            TokenRefresh tokenRefresh = (TokenRefresh) redisTemplate.opsForValue().get(Consts.REFRESH_TTL_KEY + token.hashCode());
            if (tokenRefresh != null && new Date().compareTo(tokenRefresh.getRefreshDeadline()) < 0) {
                String refreshedToken = jwtHelper.generateToken(tokenRefresh.getUsername());
                redisTemplate.delete(Consts.REFRESH_TTL_KEY + token.hashCode());
                token = refreshedToken;
                response.setHeader(Consts.AUTH_HEADER_NAME, refreshedToken);
            } else {
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                objectMapper.writeValue(response.getWriter(), Result.fail(ResultCode.TOKEN_REFRESH_TIMEOUT));
                return false;
            }
        } else if (claims == null) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            objectMapper.writeValue(response.getWriter(), Result.fail(ResultCode.TOKEN_ILLEGAL));
            return false;
        }
        //从请求中获取token中的username
        String username = jwtHelper.getUsernameFromToken(token);
        if (StringUtils.isBlank(username)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            objectMapper.writeValue(response.getWriter(), Result.fail(ResultCode.TOKEN_INVALID));
            return false;
        }
        //将当前username缓存中的有效token与当前token进行匹配，匹配失败则token无效
        if (!StringUtils.equals((CharSequence) redisTemplate.opsForHash().get(Consts.VALID_TOKEN_STORE, username), token)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            objectMapper.writeValue(response.getWriter(), Result.fail(ResultCode.TOKEN_INVALID));
            return false;
        }
        StatelessToken accessToken = new StatelessToken(username, token);
        try {
            getSubject(servletRequest, servletResponse).login(accessToken);
        } catch (AuthenticationException e) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            objectMapper.writeValue(response.getWriter(), Result.fail(ResultCode.UNAUTHORIZED));
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        objectMapper.writeValue(response.getWriter(), Result.fail(ResultCode.FORBIDDEN));
        return false;
    }


    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        //允许跨域访问
        if(httpServletRequest.getMethod().equals(RequestMethod.OPTIONS)){
            return true;
        }
        return super.preHandle(request, response);
    }

}
