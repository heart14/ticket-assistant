package com.heart.ticket.model;

/**
 * About:
 * Other:
 * Created: wfli on 2023/5/16 15:21.
 * Editored:
 */
public class SysResponse {

    private String state;
    private String code;
    private String msg;
    private Object data;

    public SysResponse() {
    }

    public SysResponse(String state, String code, String msg, Object data) {
        this.state = state;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public static SysResponse success() {
        return success(null);
    }

    public static SysResponse success(Object data) {
        return success(RespCode.SUCCESS.getCode(), RespCode.SUCCESS.msg, data);
    }

    public static SysResponse success(String code, String msg, Object data) {
        return new SysResponse(STATE_SUCCESS, code, msg, data);
    }


    public static SysResponse fail() {
        return fail(null);
    }

    public static SysResponse fail(Object data) {
        return fail(RespCode.FAIL.getCode(), RespCode.FAIL.msg, data);
    }

    public static SysResponse fail(String code, String msg, Object data) {
        return new SysResponse(STATE_FAIL, code, msg, data);
    }


    /**
     * 响应状态常量
     */
    public static final String STATE_SUCCESS = "SUCCESS";
    public static final String STATE_FAIL = "FAIL";

    /**
     * 系统错误码枚举类
     */
    enum RespCode {

        /**
         * SUCCESS          成功           0000
         * XXX_FAIL         某种操作失败    1xxx
         * XXX_INVALID      某种内容非法    2xxx
         * XXX_EXCEPTION    出现系统异常    3xxx
         */

        SUCCESS("0000", "成功"),
        FAIL("9999", "未知错误"),
        PARAM_INVALID("2001", "参数非法");

        private String code;
        private String msg;

        RespCode() {
        }

        RespCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
