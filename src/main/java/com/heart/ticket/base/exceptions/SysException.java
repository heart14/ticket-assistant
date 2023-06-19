package com.heart.ticket.base.exceptions;

/**
 * About:
 * Other:
 * Created: Administrator on 2022/3/9 17:38.
 * Editored:
 */
public class SysException extends RuntimeException {

    private static final long serialVersionUID = 29695942793488621L;


    private Integer code;

    public SysException(Integer code) {
        this.code = code;
    }

    public SysException(Integer code, String message) {
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
