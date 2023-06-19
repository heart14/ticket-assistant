package com.heart.ticket.base.common;

/**
 * About:
 * Other:
 * Created: wfli on 2023/6/19 14:20.
 * Editored:
 */
public class Constants {

    /**
     * 系统响应状态常量
     */
    public static final String STATE_SUCCESS = "SUCCESS";
    public static final String STATE_FAIL = "FAIL";

    /**
     * slf4j日志框架MDC常量字段名
     */
    public static final String FIELD_MDC_TRACE_ID = "traceId";

    /**
     * 约定http请求头中token字段名为Authorization
     */
    public static final String FIELD_JWT_TOKEN = "Authorization";
}
