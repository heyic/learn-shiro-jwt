package com.hyc.config.shiro;

import com.hyc.config.shiro.cache.AuthenCustomCache;
import com.hyc.config.shiro.cache.AuthorCustomCache;
import com.hyc.entity.Role;
import com.hyc.entity.User;
import com.hyc.entity.UserRole;
import com.hyc.repositroy.RoleRepository;
import com.hyc.repositroy.UserRepository;
import com.hyc.repositroy.UserRoleRepository;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @Author: yche
 * @Date: 2019/07/29
 * @Description 使用Jwt进行认证授权
 *
 * 注： springContextUtil的bean的创建必须优先于该类
 */
public class StatelessRealm extends AuthorizingRealm {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;


    public StatelessRealm() {
        setCachingEnabled(true);
        setAuthenticationCachingEnabled(true);
        setAuthorizationCachingEnabled(true);
        setAuthorizationCache(new AuthorCustomCache<>());
        setAuthenticationCache(new AuthenCustomCache<>());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = userRepository.findByUsername(username);
        List<UserRole> userRoles = userRoleRepository.findAllByUserId(user.getId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<Long> roleIds = userRoles.stream().map(userRole -> userRole.getRoleId()).collect(Collectors.toList());
        List<Role> roles = roleRepository.findAllByIdIsIn(roleIds);
        Set<String> roleSet = roles.stream().map(role -> role.getRoleName()).collect(Collectors.toSet());
        info.addRoles(roleSet);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        StatelessToken token = (StatelessToken) authenticationToken;
        Assert.notNull(authenticationToken, "token is null");
        String username = (String)token.getPrincipal();
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UnknownAccountException(String.format("user '%s' not found", username));
        }
        String accessToken = (String)token.getCredentials();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, accessToken, "userRealm");
        return info;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof StatelessToken;
    }


}
