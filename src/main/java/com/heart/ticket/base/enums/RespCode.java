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
    SYS_EXCEPTION(1001, "异常"),
    BIZ_FAIL(2001, "失败"),
    BIZ_INVALID(3001, "非法"),
    BIZ_SSH_EXCEPTION(2002, "SSH连接失败"),
    BIZ_MTSDK_EXCEPTION(2003, "美团请求失败"),
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
