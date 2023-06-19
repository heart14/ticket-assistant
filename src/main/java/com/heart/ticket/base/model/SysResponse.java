package com.heart.ticket.base.model;

import com.heart.ticket.base.common.Constants;
import com.heart.ticket.base.enums.RespCode;

/**
 * About:
 * Other:
 * Created: wfli on 2023/5/16 15:21.
 * Editored:
 */
public class SysResponse {

    private String state;
    private int code;
    private String msg;
    private Object data;

    public SysResponse() {
    }

    public SysResponse(String state, int code, String msg, Object data) {
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
        return success(RespCode.SUCCESS.getCode(), RespCode.SUCCESS.getMsg(), data);
    }

    public static SysResponse success(int code, String msg) {
        return success(RespCode.SUCCESS.getCode(), RespCode.SUCCESS.getMsg(), null);
    }

    public static SysResponse success(int code, String msg, Object data) {
        return new SysResponse(Constants.STATE_SUCCESS, code, msg, data);
    }


    public static SysResponse fail() {
        return fail(null);
    }

    public static SysResponse fail(Object data) {
        return fail(RespCode.FAIL.getCode(), RespCode.FAIL.getMsg(), data);
    }

    public static SysResponse fail(int code, String msg) {
        return fail(RespCode.FAIL.getCode(), RespCode.FAIL.getMsg(), null);
    }

    public static SysResponse fail(int code, String msg, Object data) {
        return new SysResponse(Constants.STATE_FAIL, code, msg, data);
    }

}
