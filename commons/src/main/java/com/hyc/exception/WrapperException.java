package com.hyc.exception;
import com.hyc.result.ResultCode;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: yche
 * @Date: 2019/07/29
 * @Description 自定义的异常
 */
public class WrapperException extends RuntimeException  {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 291977197110422770L;
    /**
     * 错误码
     */
    private ResultCode retCode = ResultCode.UNKNOWN_ERROR;

    private Logger LOGGER = LoggerFactory.getLogger(WrapperException.class);


    public WrapperException(ResultCode retCode) {
        this.retCode = retCode;
    }

    public WrapperException(Throwable e) {
        super(e);
        LOGGER.error(ExceptionUtils.getStackTrace(e));
    }

    public WrapperException(ResultCode retCode, Throwable e) {
        super(e);
        this.retCode = retCode;
        LOGGER.error(ExceptionUtils.getStackTrace(e));
    }

    public ResultCode getRetCode() {
        return retCode;
    }
}
