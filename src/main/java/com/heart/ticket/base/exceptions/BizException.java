package com.heart.ticket.base.exceptions;

/**
 * @author wfli
 * @since 2024/2/1 15:42
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 29695942793488621L;

    private Integer code;

    public BizException(Integer code) {
        this.code = code;
    }

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
