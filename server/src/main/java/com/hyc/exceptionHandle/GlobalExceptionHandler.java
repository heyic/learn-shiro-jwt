package com.hyc.exceptionHandle;

import com.hyc.exception.WrapperException;
import com.hyc.result.Result;
import com.hyc.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: yche
 * @Date: 2019/07/30
 * @Description 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 自定义异常处理器
     * @param e
     * @return
     */
    @ExceptionHandler({WrapperException.class})
    @ResponseBody
    public Result customExceptionHandler(WrapperException e){
        return createResult(e.getRetCode());
    }

    /**
     * 未知异常处理器
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result customExceptionHandler(Exception e){
        e.printStackTrace();
        return createResult(ResultCode.UNKNOWN_ERROR);
    }


    /**
     * 请求参数校验异常处理器
     * @param e
     * @return
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result postReqpkgValidExceptionHandler(MethodArgumentNotValidException e) {
        return createResult(ResultCode.INVALID_PARAM);
    }


    /**
     * 捕捉所有Shiro异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public Result handle401(ShiroException e) {
        return new Result(HttpStatus.UNAUTHORIZED.value(), "无权访问(Unauthorized):" + e.getMessage(), null);
    }

    /**
     * 单独捕捉Shiro(UnauthorizedException)异常
     * 该异常为访问有权限管控的请求而该用户没有所需权限所抛出的异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthorizationException.class)
    public Result handle401(AuthorizationException e) {
        return new Result(HttpStatus.UNAUTHORIZED.value(), "无权访问(Unauthorized):当前Subject没有此请求所需权限(" + e.getMessage() + ")", null);
    }

    /**
     * 单独捕捉Shiro(UnauthenticatedException)异常
     * 该异常为以游客身份访问有权限管控的请求无法对匿名主体进行授权，而授权失败所抛出的异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public Result handle401(AuthenticationException e) {
        return new Result(HttpStatus.UNAUTHORIZED.value(), "无权访问(Unauthorized):当前Subject是匿名Subject，请先登录(This subject is anonymous.)", null);
    }



    private Result createResult(ResultCode code) {
        Result result = new Result();
        result.setCode(code.getCode());
        result.setMsg(code.getMsg());
        return result;
    }
}
