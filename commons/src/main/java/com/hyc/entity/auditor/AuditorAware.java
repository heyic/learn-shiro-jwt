package com.hyc.entity.auditor;

import com.hyc.constant.Consts;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: yche
 * @Date: 2019/07/29
 * @Description
 */
@Component
public class AuditorAware implements org.springframework.data.domain.AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {
        String principal = (String) SecurityUtils.getSubject().getPrincipal();
        principal = StringUtils.isBlank(principal) ? Consts.DEFAULT_USERNAME : principal;
        return Optional.of(principal);
    }

}
