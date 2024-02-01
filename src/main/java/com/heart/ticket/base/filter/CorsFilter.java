package com.heart.ticket.base.filter;


import com.heart.ticket.base.common.Constants;
import com.heart.ticket.base.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * About:
 * Other:
 * Created: wfli on 2023/1/4 17:08.
 * Editored:
 */
@Slf4j
@Component // 为什么用这个@Component就可以，难道不要用@WebFilter吗
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //如果没有添加traceId，在这里进行设置traceId
        if (MDC.get(Constants.FIELD_MDC_TRACE_ID) == null) {
            MDC.put(Constants.FIELD_MDC_TRACE_ID, StringUtils.UuidLowerCase());
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.info("cors filter, method = {}, url = {}", request.getMethod(), request.getRequestURL());
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,OPTIONS,FETCH,HEAD");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, Authorization");
        filterChain.doFilter(request, response);
        //请求完成后清除traceId
        MDC.clear();
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
