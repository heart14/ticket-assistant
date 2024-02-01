package com.heart.ticket.base.model;

import com.heart.ticket.base.common.Constants;
import com.heart.ticket.base.enums.RespCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

/**
 * About:
 * Other:
 * Created: wfli on 2023/5/16 15:21.
 * Editored:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysResponse {

    /**
     * SUCCESS | ERROR
     */
    private String state;
    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 请求id
     */
    private String lid;
    /**
     * 数据
     */
    private Object data;

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
        return new SysResponse(Constants.STATE_SUCCESS, code, msg, MDC.get(Constants.FIELD_MDC_TRACE_ID), data);
    }


    public static SysResponse fail() {
        return fail(null);
    }

    public static SysResponse fail(Object data) {
        return fail(RespCode.BIZ_FAIL.getCode(), RespCode.BIZ_FAIL.getMsg(), data);
    }

    public static SysResponse fail(int code, String msg) {
        return fail(code, msg, null);
    }

    public static SysResponse fail(int code, String msg, Object data) {
        return new SysResponse(Constants.STATE_FAIL, code, msg, MDC.get(Constants.FIELD_MDC_TRACE_ID), data);
    }

}
