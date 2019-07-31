package com.hyc.config.jwt;

import com.hyc.config.shiro.TokenRefresh;
import com.hyc.constant.Consts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yche
 * @Date: 2019/07/29
 * @Description
 */
@Component
public class JwtHelper {

    @Value("${spring.application.name}")
    private String appName;

    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

    private final JwtProperties jwtProperties;

    private final RedisTemplate redisTemplate;

    @Autowired
    public JwtHelper(JwtProperties jwtProperties, RedisTemplate redisTemplate) {
        this.jwtProperties = jwtProperties;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 从request中获取token
     * @param request：请求体
     * @return token
     */
    public String getToken(HttpServletRequest request){
        String authHeader = request.getHeader(jwtProperties.getAuthHeader());
        if(authHeader != null && StringUtils.startsWithIgnoreCase(authHeader, Consts.TOKEN_PREFIX)){
            return authHeader.substring(7);
        }
        return null;
    }

    /**
     * 从token中获取username信息
     * @param token
     * @return username
     */
    public String getUsernameFromToken(String token){
        final Claims claims;
        try {
            return this.getClaimsFromToken(token).getSubject();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 获取声明的内容
     * @param token
     * @return
     */
    public Claims getClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(String username){
        String token = Jwts.builder()
                .setIssuer(appName)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(getExpirationDate())
                .signWith(signatureAlgorithm, jwtProperties.getSecret())
                .compact();
        TokenRefresh tokenRefresh = new TokenRefresh(username, this.getRefreshDeadline());
        redisTemplate.opsForValue().set(Consts.REFRESH_TTL_KEY + token.hashCode(), tokenRefresh, jwtProperties.getRefreshIn(), TimeUnit.MINUTES);
        redisTemplate.opsForHash().put(Consts.VALID_TOKEN_STORE, username, token);
        return token;
    }

    /**
     * 获取过期时间戳
     * @param minutes 预设的过期时间，单位：分钟
     * @return 预期的过期时间戳
     */
    private Long generateTime(int minutes) {
        return System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(minutes);
    }

    /**
     * 得到过期日期时间
     * @return 预期的过期日期时间信息
     */
    private Date getExpirationDate() {
        return new Date(generateTime(jwtProperties.getExpiredIn()));
    }

    /**
     * 得到刷新token截止时间
     * @return token可刷新截止时间
     */
    private Date getRefreshDeadline() {
        return new Date(generateTime(jwtProperties.getRefreshIn()));
    }
}
