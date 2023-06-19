package com.heart.ticket.base.enums;

/**
 * About:
 * Other:
 * Created: Administrator on 2022/3/9 17:00.
 * Editored:
 */
public enum RespCode {

    /**
     * SUCCESS          成功           0000
     * XXX_FAIL         某种操作失败    1xxx
     * XXX_INVALID      某种内容非法    2xxx
     * XXX_EXCEPTION    出现系统异常    3xxx
     */
    SUCCESS(1000, "成功"),
    FAIL(1001, "失败"),
    INVALID(3001, "非法"),
    EXCEPTION(5001, "异常"),
    MTSDK_EXCEPTION(5002, "美团请求异常"),
    END(9999, "END LINE");

    private int code;
    private String msg;

    RespCode() {
    }

    RespCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
