package com.heart.ticket.base.exceptions;

import com.heart.ticket.base.enums.RespCode;
import com.heart.ticket.base.model.SysResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * About:
 * Other:
 * Created: Administrator on 2022/3/9 17:35.
 * Editored:
 */
@Slf4j
@RestControllerAdvice
public class SysExceptionHandler {

    @ExceptionHandler({SysException.class})
    public SysResponse sysExceptionHandler(SysException e) {
        //手动抛出自定异常
        log.error("** sysExceptionHandler ** :{}", e.getMessage(), e);
        return SysResponse.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({BizException.class})
    public SysResponse bizExceptionHandler(BizException e) {
        //手动抛出自定异常
        log.error("** bizExceptionHandler ** :{}", e.getMessage(), e);
        return SysResponse.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public SysResponse exceptionHandler(Exception e) {
        //系统异常
        log.error("** exceptionHandler ** :{}", e.getMessage(), e);
        return SysResponse.fail(RespCode.SYS_EXCEPTION.getCode(), RespCode.SYS_EXCEPTION.getMsg());
    }


}
